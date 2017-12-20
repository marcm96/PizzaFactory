import React from 'react';
import {
    View,
    TouchableOpacity,
    ListView,
    StyleSheet,
    Button,
    AsyncStorage,
    FlatList,
    RefreshControl,
    ScrollView,
    Text
} from 'react-native';
import { data } from '../../demoData';
import PizzaItem from "./PizzaItem";

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
        console.log(this.state.newPizzas);
        return this.state.newPizzas;
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
                                        </Text>
                                    </View>
                                </ScrollView>
                        }
                        extraData = {this.state.newPizzas}
                    />

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
