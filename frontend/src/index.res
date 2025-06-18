%%raw(`import './index.css';`)

let rootQuery = ReactDOM.querySelector("#root")

switch rootQuery {
| Some(rootElement) =>
  let root = ReactDOM.Client.createRoot(rootElement)
  ReactDOM.Client.Root.render(root, <App />)
| None => failwith("DOM root is missing")
}