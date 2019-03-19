//
//  ViewController.swift
//  ITHomeApp
//
//  Created by Ralph Rouwen on 19/03/2019.
//  Copyright © 2019 Ralph Rouwen. All rights reserved.
//

import UIKit

struct Device: Decodable{
    let identifier: String
    let name: String
    let device_type: String
    let controller_name: String
    let ip_address: String
    let room_identifier: String
}

class ViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let url = "http://192.168.178.81/registry/devices"
        let urlObj = URL(string: url)
        
        URLSession.shared.dataTask(with: urlObj!) {(data, response, error) in
            
            if let jsonObj = try? JSONSerialization.jsonObject(with: data!, options: .allowFragments) as? NSDictionary {
                print(jsonObj!.value(forKey: "devices"))
            }
            }.resume()
    }
}

