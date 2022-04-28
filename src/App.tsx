import React from 'react';
import './App.css';
import Header from './Content/HeaderBar';
import QuestionTable from './Content/QuestionTable';

function App() {
  return (
    <div className="App">
      <header>
        <Header />
      </header>
      <body className='content'>
        <div className='content_box'>
          <QuestionTable />
        </div>
      </body>
    </div>
  );
}

export default App;
