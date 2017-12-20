import React from 'react';
import {TextInput, View, Text, Button, Linking} from 'react-native'

let updateItem = function (params) {
    params.dataSource[params.rowData.id] = {};
    params.dataSource[params.rowData.id] = {
        id: params.rowData.id,
        name: params.rowData.name,
        ingredients: params.rowData.ingredients,
        weight: params.rowData.weight,
        price: params.rowData.price
    };
    return params.dataSource;
};
export default class PizzaDetails extends React.Component {
    static  navigationOptions = ({ navigation }) => ({
        title: `Details for ${navigation.state.params.rowData.name}`
    });


    render() {
        const { params } = this.props.navigation.state;
        const { goBack } = this.props.navigation;
        return(
            <View>
                <Text>Pizza</Text>
                <TextInput>{params.rowData.name}</TextInput>
                <Text>Ingredients:</Text>
                <TextInput>{params.rowData.ingredients}</TextInput>
                <Text>Weight</Text>
                <TextInput>{params.rowData.weight}</TextInput>
                <Text>Price</Text>
                <TextInput>{params.rowData.price}</TextInput>

                <Button
                    onPress = {() => goBack()}
                    title = "Update"
                    color= "#841584"
                />

                <Button
                    onPress={() => openGmail(params.rowData)}
                    title = "Send Feedback"
                    color = "#007fe0"
                />
            </View>
        )
    }
}

function openGmail(data) {
    const body = `You want to place an order for ${data.name}. \n Ingredients: ${data.ingredients} \n Total: ${data.price} `
    let emailUrl = `mailto:pizza_factory@pizza.com?subject=New Order&body=${body}`
    Linking.openURL(emailUrl);
}
