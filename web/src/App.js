import banner from './balloons.jpeg';
import logo from './logo.svg'
import './App.css';
import "animate.css/animate.min.css";
import MyComponent from './MyComponent.js';
import ScrollAnimation from 'react-animate-on-scroll';
import Form from './Form.js';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <div className="container">
            <img src={banner} className="Birthday-logo" alt="logo" />
            <div class="centered">Nickname and Birthday Database</div>
        </div>
      </header>
        <MyComponent/>
        <Form/>
    </div>
  );
}

export default App;
