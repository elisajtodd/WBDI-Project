import React from 'react';
import Table from 'react-bootstrap/Table';
import './QuestionTable.css';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

export default class QuestionTable extends React.Component {
    render() {
        return (
            <div className='question_table'>
                <Form>
                    <Table striped>
                        <thead>
                            <tr>
                                <th colSpan={2}><h3>Questions</h3></th>
                                <th>
                                    <Form.Group className='count_sort'>
                                        <Form.Label>Show:</Form.Label>
                                        <Form.Control type="text" pattern="[0-9]*" placeholder="10" />
                                    </Form.Group>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td width={20}>1</td>
                                <td>
                                    <div className='question'>
                                        Where does a person with occupation
                                        <Form.Select className='place' size="lg">
                                            <option>Software Developer</option>
                                            <option>Accountant</option>
                                            <option>Pilot</option>
                                        </Form.Select>
                                        typically live?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>
                                    <div className='question'>
                                        What are the highest paying places to move to find occupation
                                        <Form.Select className='place' size="lg">
                                            <option>Software Developer</option>
                                            <option>Accountant</option>
                                            <option>Pilot</option>
                                        </Form.Select>
                                        ?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>
                                    <div className='question'>
                                        What are the highest paying occupations in location
                                        <Form.Select className='place' size="lg">
                                            <option>El Paso</option>
                                            <option>Seattle</option>
                                            <option>Phoenix</option>
                                        </Form.Select>
                                        ?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>
                                    <div className='question'>
                                        What is the median income of occupation
                                        <Form.Select className='place' size="lg">
                                            <option>Software Developer</option>
                                            <option>Accountant</option>
                                            <option>Pilot</option>
                                        </Form.Select>
                                        in location
                                        <Form.Select className='place' size="lg">
                                            <option>El Paso</option>
                                            <option>Seattle</option>
                                            <option>Phoenix</option>
                                        </Form.Select>
                                        ?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td>
                                    <div className='question'>
                                        What occupations are available in location
                                        <Form.Select className='place' size="lg">
                                            <option>El Paso</option>
                                            <option>Seattle</option>
                                            <option>Phoenix</option>
                                        </Form.Select>
                                        ?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td>
                                    <div className='question'>
                                        What is the average income in location
                                        <Form.Select className='place' size="lg">
                                            <option>El Paso</option>
                                            <option>Seattle</option>
                                            <option>Phoenix</option>
                                        </Form.Select>
                                        for occupation
                                        <Form.Select className='place' size="lg">
                                            <option>Software Developer</option>
                                            <option>Accountant</option>
                                            <option>Pilot</option>
                                        </Form.Select>
                                        ?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td>
                                    <div className='question'>
                                        How many individuals with occupation
                                        <Form.Select className='place' size="lg">
                                            <option>Software Developer</option>
                                            <option>Accountant</option>
                                            <option>Pilot</option>
                                        </Form.Select>
                                        live in
                                        <Form.Select className='place' size="lg">
                                            <option>El Paso</option>
                                            <option>Seattle</option>
                                            <option>Phoenix</option>
                                        </Form.Select>
                                        ?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                            <tr>
                                <td>8</td>
                                <td>
                                    <div className='question'>
                                        What occupations are the lowest pay in location
                                        <Form.Select className='place' size="lg">
                                            <option>El Paso</option>
                                            <option>Seattle</option>
                                            <option>Phoenix</option>
                                        </Form.Select>
                                        ?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                            <tr>
                                <td>9</td>
                                <td>
                                    <div className='question'>
                                        Which occupations are
                                        <Form.Select className='place' size="lg">
                                            <option>High-Paying</option>
                                            <option>Low-Paying</option>
                                            <option>Medium-Paying</option>
                                        </Form.Select>
                                        ?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                            <tr>
                                <td>10</td>
                                <td width={1300}>
                                    <div className='question'>
                                        What is the percentage of women for occupation
                                        <Form.Select className='place' size="lg">
                                            <option>Software Developer</option>
                                            <option>Accountant</option>
                                            <option>Pilot</option>
                                        </Form.Select>
                                        ?
                                    </div>
                                </td>
                                <td className='search'>
                                    <Button variant="primary">
                                        Answer
                                    </Button>
                                </td>
                            </tr>
                        </tbody>
                    </Table>
                </Form>
            </div>
        );
    }
}