import React from 'react';
import {TextInput,
    View,
    Text,
    Button,
    Linking,
    AsyncStorage
} from 'react-native'
import {Pizza} from "../model/Pizza";

export default class AddPizza extends React.Component {
    static navigationOptions = {
        //title: 'Home',
        header: null,
    };


    async save() {
        let response = await AsyncStorage.getItem('@MyStore:key');
        let pizzas = JSON.parse(response);
        const count = pizzas.length;
        pizzas.push({
            key: pizzas[count - 1].key + 1,
            pizza: new Pizza(this.state.newName, this.state.newDescription, this.state.newWeight, this.state.newPrice)
        });
        AsyncStorage.setItem('@MyStore:key', JSON.stringify(pizzas));
    }

    render() {
        const {params} = this.props.navigation.state;
        const {goBack} = this.props.navigation;

        return (
        <View>
            <Text>Pizza</Text>
            <TextInput onChangeText={(text) => this.setState({newName: text})}>
            </TextInput>

            <Text>Ingredients:</Text>
            <TextInput onChangeText={(text) => this.setState({newDescription: text})}>
            </TextInput>

            <Text>Weight</Text>
            <TextInput onChangeText={(text) => this.setState({newWeight: text})}>
            </TextInput>

            <Text>Price</Text>
            <TextInput onChangeText={(text) => this.setState({newPrice: text})}>
            </TextInput>

            <Button
                onPress ={
                    async () => {
                        await this.save();
                        params.refresh();
                        goBack();
                    }
                }
                title = "Save"
                color= "#841584"
            />
        </View>

        )

    }

}


