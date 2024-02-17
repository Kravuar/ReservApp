import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import MainPage from './components/pages/MainPage';

function App() {
  return (
      <Router>
        <div className="App">
          <Routes>
            <Route path="/">
              <MainPage />
            </Route>
          </Routes>
        </div>
      </Router>
  );
}

export default App;
