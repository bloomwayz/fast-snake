<div align="center">
  <h1> 🟩 SuperSnake 🟥 </h1>
  <p align="center">
    <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"/>
    <img src="https://img.shields.io/badge/rescript-E6484F?style=for-the-badge&logo=rescript&logoColor=white"/>
    <img src="https://img.shields.io/badge/react-222222?style=for-the-badge&logo=react&logoColor=61DAFB"/>
  </p>
</div>
<br></br>


## 🍎 About

- **SuperSnake** is a snake game variation which has a multi-stage system.
- The more food the snake eats, the faster it will run!


## ⏯️ Rules

- A sequence of green blocks is the snake, and a red block is the food.
- If you press the **arrow key**, the snake runs in that direction.
- When the snake **gets the food**, it will be **one block longer**.
- The game is over when the snake **hit the wall** or it is **collided with itself**.
- *Sometimes* the level goes up and **the snake gets faster…**


## 🕹️ Key bindings

| Keys | Functions |
|---|---|
| ⬆️, `W`, `K` | Go up |
| ⬅️, `A`, `H` | Go left |
| ⬇️, `S`, `J` | Go down |
| ➡️, `D`, `L` | Go right |
| `esc`, `space`, `P` | Pause/Resume |


## How to Run

0. **SuperSnake** needs Node.js, npm, and Gradle. If you do not have them, please check the installation guides below.
   - [How to install Node.js and npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
   - [How to install Gradle](https://gradle.org/install/)

1. Build and activate the backend server:
   ```sh
     λ cd backend && gradle run
   ```

2. Turn on another terminal, and install dependencies via npm:
   ```sh
     λ cd frontend && npm install
   ```
   
3. Build ReScript files:
   ```sh
     λ npm run re:build
   ```

4. Activate the frontend server:
   ```sh
     λ npm run start
   ```

5. Finally, you can see **SuperSnake** on `http://localhost:3000`.


## 👷 Trivia

- **SuperSnake** is a term project for SNU undergraduate course *Computer Programming*.
