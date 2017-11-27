import React from 'react';
import { StyleSheet } from 'react-native';

import { StackNavigator } from 'react-navigation';

import PizzaList from './pages/pizza_list/PizzaList';
import PizzaDetails from "./pages/pizza_list/PizzaDetails";

export const RootNavigator = StackNavigator({
    Home : { screen: PizzaList},
    Details: { screen: PizzaDetails}
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
