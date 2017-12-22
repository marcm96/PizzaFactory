import React from 'react';
import { StyleSheet } from 'react-native';

import { StackNavigator } from 'react-navigation';

import PizzaList from './pages/pizza_list/PizzaList';
import PizzaDetails from "./pages/pizza_list/PizzaDetails";
import AddPizza from "./pages/pizza_list/AddPizza";

export const RootNavigator = StackNavigator({
    Home : { screen: PizzaList },
    Details: { screen: PizzaDetails },
    AddPizza: { screen: AddPizza }
});

export default class App extends React.Component {
  render() {
    return (
      <RootNavigator/>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center'
  }
});
