//
//  ViewController.swift
//  JOSNSwiftDemo
//
//  Created by Shinkangsan on 12/5/16.
//  Copyright © 2016 Sheldon. All rights reserved.
//

import UIKit

class ViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {

    final let urlString = "http://192.168.178.130/registry/devices"
    
    @IBOutlet weak var tableView: UITableView!
    
    var nameArray = [String]()
    var dobArray = [String]()
    var imgURLArray = [String]()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.downloadJsonWithURL()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    func downloadJsonWithURL() {
        let url = NSURL(string: urlString)
        URLSession.shared.dataTask(with: (url as? URL)!, completionHandler: {(data, response, error) -> Void in
            
            if let jsonObj = try? JSONSerialization.jsonObject(with: data!, options: .allowFragments) as? NSDictionary {
                print(jsonObj!.value(forKey: "devices"))
                
                if let actorArray = jsonObj!.value(forKey: "devices") as? NSArray {
                    for actor in actorArray{
                        if let actorDict = actor as? NSDictionary {
                            if let name = actorDict.value(forKey: "name") {
                                self.nameArray.append(name as! String)
                            }
                            if let name = actorDict.value(forKey: "device_type") {
                                self.dobArray.append(name as! String)
                            }
                        }
                    }
                }
                
                OperationQueue.main.addOperation({
                    self.tableView.reloadData()
                })
            }
        }).resume()
    }


    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return nameArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! TableViewCell
        cell.nameLabel.text = nameArray[indexPath.row]
        cell.dobLabel.text = dobArray[indexPath.row]
        
//        let imgURL = NSURL(string: imgURLArray[indexPath.row])
//
//        if imgURL != nil {
//            let data = NSData(contentsOf: (imgURL as? URL)!)
//            cell.imgView.image = UIImage(data: data as! Data)
//        }
        
        return cell
    }
    
    ///for showing next detailed screen with the downloaded info
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "DetailViewController") as! DetailViewController
//        vc.imageString = imgURLArray[indexPath.row]
        vc.nameString = nameArray[indexPath.row]
        vc.dobString = dobArray[indexPath.row]
        
        self.navigationController?.pushViewController(vc, animated: true)
    }
}

