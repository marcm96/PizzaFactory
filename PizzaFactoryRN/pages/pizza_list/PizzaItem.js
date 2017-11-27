import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet, Button } from 'react-native';

export default class PizzaItem extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            pizzaName: this.props.data.name,
            pizzaIngredients: this.props.data.ingredients,
            pizzaWeight: this.props.data.weight,
            pizzaPrice: this.props.data.price,
        }
    }

    render() {
        return(
        <TouchableOpacity>
            <View style={{borderWidth : 2}}>
                <Text style={styles.textLeft}>{this.state.pizzaName}</Text>
                <Text style={styles.textLeft}>{this.state.pizzaIngredients}</Text>
                <Text style={styles.textLeft}>{this.state.pizzaWeight}</Text>
                <Text style={styles.textRight}>{this.state.pizzaPrice}</Text>
            </View>
        </TouchableOpacity>

        );
    }
}

const styles = StyleSheet.create({
    container: {
        padding: 10,
        margin: 5,
        backgroundColor: '#ECEFF1',
        borderRadius: 2,
        borderWidth: 1,
        borderColor: "#E0E0E0"
    },
    textLeft: {
        color: '#263238',
        textAlign: "left",
        fontSize: 18
    },
    textRight: {
        color: '#263238',
        textAlign: "right"
    },
    body: {
        marginTop: 20
    },
    toolbar: {
        height: 70,
        backgroundColor: "#01579B",
        flex: 0,
        flexDirection: "row",
        alignItems: "flex-end"
    },
    toolbarContent: {
        flex:1,
        alignItems: "center"
    },
    toolbarText: {
        color: "#fff",
        fontSize: 20,
        margin: 10,
        fontWeight: "bold"
    }
});
