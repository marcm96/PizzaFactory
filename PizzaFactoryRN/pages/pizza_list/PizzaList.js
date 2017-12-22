import React from 'react';
import {
    View,
    TouchableOpacity,
    ListView,
    StyleSheet,
    AsyncStorage,
    FlatList,
    RefreshControl,
    ScrollView,
    Text,
    Alert
} from 'react-native';
import { data } from '../../demoData';

const ds = new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 });


export default class PizzaList extends React.Component {

    static navigationOptions = {
        title: 'Welcome',
        header: null
    };

    constructor(props) {
        super(props);
        // this.add();
        this._onRefresh = this._onRefresh.bind(this);
        this.state = {
            refreshing: false,
            newPizzas: [],
            loading: true,
        };
    }

    async add(){
        AsyncStorage.setItem('@MyStore:key', JSON.stringify(data));
    }

    async findByName(name) {
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

    async deletePizza(idx){
        let response = await AsyncStorage.getItem('@MyStore:key');
        let pizzas =  JSON.parse(response);
        pizzas.splice(idx, 1);
        AsyncStorage.setItem('@MyStore:key', JSON.stringify(pizzas));
    }

    _onRefresh() {
        this.setState({refreshing:true});
        this.setState({refreshing:false});
        this.retrieveContent();
    }

    retrieveContent() {
        AsyncStorage.getItem('@MyStore:key').then((value) => {
            this.setState({newPizzas: JSON.parse(value)});
        }).catch((error) => {
            console.log("Unable to retrieve the content" + error);
        });
    }

    componentWillMount(){
        this.getItems();
    }

    getItems(){
        AsyncStorage.getItem('@MyStore:key').then((value) => {
            this.setState({newPizzas: JSON.parse(value)});
            this.setState({loading: false});
        }).catch((error) => {
            console.log("Unable to retrieve the content" + error);
        });
    }



    returnData(){
        return this.state.newPizzas;
    }

    showAlert(name){
        Alert.alert('INFO','Are you sure you want to delete this item from the menu list?',
            [
                {text: 'Yes',
                    onPress: async () =>{
                        var idx = await this.findByName(name);
                        await this.deletePizza(idx);
                        this._onRefresh();
                    }},
                {text: 'No',
                    onPress: () => console.log("Then selected item was not deleted!")}
            ],
            {cancelable: false}
        )
    }


    render() {
        const { navigate } = this.props.navigation;

        if (this.state.loading !== true){
            return(
                <View style={styles.container}>
                    <FlatList
                        refreshControl={
                            <RefreshControl
                                refreshing={this.state.refreshing}
                                onRefresh={this._onRefresh.bind(this)}
                            />
                        }
                        data = { this.returnData() }
                        renderItem={
                            ({item}) =>
                                <ScrollView>
                                    <View>
                                        <Text  onPress={() => navigate('Details', {pizza: item.pizza, refresh: this._onRefresh})}>
                                            {item.pizza.name}
                                            {item.pizza.description}
                                            {item.pizza.weight}
                                            {item.pizza.price}
                                        </Text>
                                        <View>
                                            <TouchableOpacity
                                                onPress={ () => this.showAlert(item.pizza.name)}>
                                                <Text> X </Text>
                                            </TouchableOpacity>
                                        </View>
                                    </View>
                                </ScrollView>
                        }
                        extraData = {this.state.newPizzas}
                    />

                    <View>
                        <TouchableOpacity onPress={() =>
                            navigate('AddPizza',{refresh: this._onRefresh})}>
                            <Text > Add pizza </Text>
                        </TouchableOpacity>
                    </View>

                </View>

            )
        } else {
            return(
                <View>
                    <Text> Loading </Text>
                </View>
            )
        }


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
