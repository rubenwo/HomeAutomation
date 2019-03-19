import React from 'react';
import axios from 'axios';

import { Platform, StyleSheet, Text, View, FlatList, Alert, ActivityIndicator} from 'react-native';
import ApiClient from "./api/ApiClient";

export const apiClient = new ApiClient("http://localhost:8005");

export default class App extends React.Component{
 

 constructor(props) {
   super(props);

   state = {
    isLoading: true,
  }
 }

 async componentDidMount() {
    let response = await apiClient.fetchDevices();
    this.setState({
      isLoading: false,
      dataSource: response
    })
    console.log(this.state.dataSource)
 }

  getGridViewItem(light_name) {
    Alert.alert(light_name);
  }


 render() {
  if(this.state.isLoading) {
    return (
      <View style = {styles.ActivityIndicator_Style}>
        <ActivityIndicator size = "large" />
      </View>
    )
  }

   return (
     <View>

     </View>
   );
 }
}

const styles = StyleSheet.create({

  ActivityIndicator_Style : {
    left: 0,
    right: 0,
    top: 0,
    bottom: 0,
    postion: 'absolute',
    alignItems: 'center',
    justifyContent: 'center'
  }
});
