import React from 'react';
import {TextInput,
    View,
    Text,
    Button,
    Linking,
    AsyncStorage,
    TimePickerAndroid
} from 'react-native'

export default class PizzaDetails extends React.Component {
    static  navigationOptions = ({ navigation }) => ({
        title: `Details for ${navigation.state.params.name}`
    });

    static async findByName(name) {
        let response = await AsyncStorage.getItem('@MyStore:key');
        let pizzas = JSON.parse(response);
        var count = pizzas.length;
        for(var i = 0; i < count ; i++) {
            if(pizzas[i].pizza.name === name) {
                return i;
            }
        }
        return -1;
    }

    static async updatePizzaDescription(index, description) {
        let response = await AsyncStorage.getItem('@MyStore:key');
        let pizzas =  JSON.parse(response);

        pizzas[index].pizza.description = description;
        AsyncStorage.setItem('@MyStore:key', JSON.stringify(pizzas));
    }

    static async updatePizzaWeight(index, weight) {
        let response = await AsyncStorage.getItem('@MyStore:key');
        let pizzas =  JSON.parse(response);

        pizzas[index].pizza.weight = weight;
        AsyncStorage.setItem('@MyStore:key', JSON.stringify(pizzas));
    }

    static async updatePizzaPrice(index, price) {
        let response = await AsyncStorage.getItem('@MyStore:key');
        let pizzas =  JSON.parse(response);

        pizzas[index].pizza.price = price;
        AsyncStorage.setItem('@MyStore:key', JSON.stringify(pizzas));
    }


    render() {
        const { navigate } = this.props.navigation;
        const { params } = this.props.navigation.state;
        const { goBack } = this.props.navigation;
        var pizza = params ? params.pizza : "<undefined>";
        return (
            <View>
                <Text>Pizza</Text>
                <TextInput onChangeText={(text) => this.setState({newName: text})}>
                    {pizza.name}
                </TextInput>

                <Text>Ingredients:</Text>
                <TextInput onChangeText={(text) => this.setState({newDescription: text})}>
                    {pizza.description}
                </TextInput>

                <Text>Weight</Text>
                <TextInput onChangeText={(text) => this.setState({newWeight: text})}>
                    {pizza.weight}
                </TextInput>

                <Text>Price</Text>
                <TextInput onChangeText={(text) => this.setState({newPrice: text})}>
                    {pizza.price}
                </TextInput>

                <Text>Time</Text>
                <TimePickerAndroid>
                    open =

                </TimePickerAndroid>


                <Button
                    onPress = {
                        async () => {
                            const id = await PizzaDetails.findByName(params.pizza.name);
                            if (this.state.newDescription) {
                                await PizzaDetails.updatePizzaDescription(id, this.state.newDescription);
                            }
                            if (this.state.newWeight) {
                                await PizzaDetails.updatePizzaWeight(id, this.state.newWeight);
                            }
                            if (this.state.newPrice) {
                                await PizzaDetails.updatePizzaPrice(id, this.state.newPrice);

                            }
                            params.refresh();
                            goBack();
                        }
                    }
                    title = "Update"
                    color= "#841584"
                />

                <Button
                    onPress={() => openGmail(pizza)}
                    title = "Place Order"
                    color = "#007fe0"
                />
            </View>
        )
    }
}

function openGmail(data) {
    const body = `You want to place an order for ${data.name}. \n Ingredients: ${data.ingredients} \n Total: ${data.price} `;
    let emailUrl = `mailto:pizza_factory@pizza.com?subject=New Order&body=${body}`;
    Linking.openURL(emailUrl);
}
