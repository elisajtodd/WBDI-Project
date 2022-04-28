import React from 'react';
import logo from '../jobgps.svg';
import './HeaderBar.css';

export default class HeaderBar extends React.Component {
    render() {
        return (
            <div className='header'>
                <img src={logo} className="pageLogo" alt="logo" />
                <h1>Job GPS</h1>
            </div>
        );
    }
}