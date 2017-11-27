import React from 'react';
import { View, ListView, StyleSheet, Button} from 'react-native';
import { data } from '../../demoData';
import PizzaItem from "./PizzaItem";

const ds = new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 });


export default class PizzaList extends React.Component {

    static navigationOptions = {
        title: 'Welcome'
    };

    constructor(props) {
        super(props);
        this.state = {
            dataSource: ds.cloneWithRows(data),
        };
    }

    render() {
        const { navigate } = this.props.navigation;
        return (
            <View>

                <ListView
                    dataSource={this.state.dataSource}
                    renderRow={(rowData, sectionId, rowId) =>
                    <View>
                        <PizzaItem
                            data = {rowData}
                        />
                        <Button
                            onPress = {() => navigate('Details', {rowData: rowData})}
                            title = "Details"
                            color= "#841584"
                        />
                    </View>
                    }
                />
            </View>
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
