// src/Nav.res
@react.component
let make = () => {
  let handleClick = React.useCallback0(path => {
    RescriptReactRouter.push(path)
  })

  <nav className="flex justify-center space-x-2 w-full mx-auto">
    <button onClick={_ => handleClick("cat")} className="bg-blue-300 p-2 mx-1 rounded text-white">
      {React.string("cat")}
    </button>
    <button onClick={_ => handleClick("dog")} className="bg-blue-300 p-2 mx-1 rounded text-white">
      {React.string("dog")}
    </button>
  </nav>
}
