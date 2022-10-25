import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';

const App = () => {

  const [solutions, setSolutions] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);

    fetch('math/getAll')
      .then(response => response.json())
      .then(data => {
        setSolutions(data);
        setLoading(false);
      })
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <div className="App-intro">
          <h2>Solutions list</h2>
          {solutions.map(solution =>
            <div key={solution.id}>
              {solution.solutionValues}
            </div>
          )}
        </div>
      </header>
    </div>
  );
}

export default App;