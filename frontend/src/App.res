%%raw(`import './App.css';`)
@val external window: 'a = "window"

exception InvalidState

type gameState = Playing | Paused | GameOver

module Position = {
  type t = {
    x: int,
    y: int,
  }

  let make = (x, y) => {x, y}
}

module Direction = {
  type t = Up | Down | Left | Right

  let toString = dir =>
    switch dir {
    | Up => "Up"
    | Down => "Down"
    | Left => "Left"
    | Right => "Right"
    }
}

module Grid = {
  type cell = Snake | Food | Empty
  type t = array<array<cell>>
  type raw = array<array<string>>

  let x_max = 15
  let y_max = 17

  let init = () =>
    Array.make_matrix(x_max, y_max, Empty)

  let find = (pos, grid) => {
    open Position
    if ((0 <= pos.x && pos.x < x_max) || (0 <= pos.y && pos.y < y_max)) {
      Some (grid[pos.x][pos.y])
    } else {
      None
    }
  }

  let toString = cell =>
    switch cell {
    | Snake => "snake"
    | Food => "apple"
    | Empty => " "
    }
}

module State = {
  type t = {
    grid: Grid.t,
    score: int,
    level: int,
    gameState: gameState,
  }

  let init = () => {
    grid: Grid.init(),
    score: 0,
    level: 1,
    gameState: Paused,
  }
}

module Decode = {
  open Json.Decode

  let cell = str => {
    open Grid

    switch str {
    | "Snake" => Snake
    | "Food" => Food
    | _ => Empty
    }
  }

  let gameState = str =>
    switch str {
    | "Playing" => Playing
    | "Paused" => Paused
    | "GameOver" => GameOver
    | _ => raise(InvalidState)
    }

  let state = object(field => {
    State.grid: field.required(. "grid", array(array(string->map(cell)))),
    State.score: field.required(. "score", int),
    State.level: field.required(. "level", int),
    State.gameState: field.required(. "gameState", string->map(gameState)),
  })
}

let interval = ref(None)
let tickInterval = 60

@react.component
let make = () => {
  let (state, setState) = React.useState(State.init)
  let (dir, setDir) = React.useState(_ => Direction.Right)

  let fetchGameData = async () => {
    let response = await Fetch.get("http://localhost:7070/game-data")
    let json = await response->Fetch.Response.json
    switch Json.decode(json, Decode.state) {
    | Ok(newState) => setState(_ => newState)
    | Error(error) => raise(InvalidState)
    }
  }

  let postJson = async (point, data) => {
    open Fetch

    let response = await fetch(
      "http://localhost:7070/" ++ point,
      {
        method: #POST,
        body: data->Js.Json.stringifyAny->Belt.Option.getExn->Body.string,
        headers: Headers.fromObject({
          "Content-type": "application/json",
        }),
      },
    )

    await response->Response.json
  }

  let initialize = () => {
    setState(_ => State.init());
    postJson("initialize", {
      "initialized": Js.Json.boolean(true),
    })->ignore
  }

  let togglePause = isPaused => {
    postJson("toggle-pause", {
      "isPaused": Js.Json.boolean(isPaused)
    })->ignore
  }

  let changeDirection = dir => {
    switch state.gameState {
    | Paused => togglePause(false)->ignore
    | _ => ()
    }
    setDir(_ => dir)
    postJson("set-direction", {
      "direction": Direction.toString(dir),
    })->ignore
  }

  let startInterval = () => {
    interval := Js.Global.setInterval(() => {
        fetchGameData()->ignore
      }, tickInterval)->Some
  }

  React.useEffect0(() => {
    initialize()->ignore
    window["addEventListener"]("keydown", event => {
      switch state.gameState {
      | Playing | Paused => {
        switch event["key"] {
        | "w" | "ArrowUp" | "k" => changeDirection(Up)
        | "s" | "ArrowDown" | "j" => changeDirection(Down)
        | "a" | "ArrowLeft" | "h" => changeDirection(Left)
        | "d" | "ArrowRight" | "l" => changeDirection(Right)
        | "p" | "Escape" | " " => togglePause(true)
        | _ => togglePause(false)
        }
      }
      | GameOver => ()
      }
    })->ignore
    startInterval()
    Some(
      () => {
        let _ = interval.contents->Belt.Option.map(Js.Global.clearInterval)
      },
    )
  })

  <div className="App">
    <h1> {"Snake Game"->React.string} </h1>
    <h3> {`Score: ${Belt.Int.toString(state.score)}`->React.string} </h3>
    <h3> {`Level: ${Belt.Int.toString(state.level)}`->React.string} </h3>
    <div style={ReactDOM.Style.make(~display="flex", ~justifyContent="center", ())}>
      <div
        style={ReactDOM.Style.make(
          ~display="flex",
          ~flexWrap="wrap",
          ~width="340px",
          ~border="1px solid black",
          (),
        )}>
        {state.grid
        ->Belt.Array.mapWithIndex((i, row) => {
          row->Belt.Array.mapWithIndex((j, cell) => {
            <div
              style={ReactDOM.Style.make(
                ~width="20px",
                ~height="20px",
                ~backgroundColor=switch cell {
                | Food => "red"
                | Snake => "green" // show occupied cell over apple
                | _ => "gray"
                },
                (),
              )}
            />
          })
          ->React.array
        })
        ->React.array}
      </div>
    </div>
    {if state.gameState == Paused {
      <>
        <h3 style={ReactDOM.Style.make(~color="black", ())}> {"Press arrow key to start"->React.string} </h3>
      </>
    } else if state.gameState == GameOver {
      <>
        <h1 style={ReactDOM.Style.make(~color="red", ())}> {"Game Over!"->React.string} </h1>
        <button
          style={ReactDOM.Style.make(
            ~padding="1rem",
            ~border="0",
            ~background="lightblue",
            ~fontWeight="bold",
            (),
          )}
          onClick={_ => initialize()->ignore}>
          {"RETRY?"->React.string}
        </button>
      </>
    } else {
      <> </>
    }}
  </div>
}
