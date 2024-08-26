import SwiftUI
import shared



struct ContentView: View {
    @State private var deviceId : String?
    @State private var inProgress : Bool = false
    
    var body: some View {
        VStack(alignment : .center){
            Button( action: {
                Task {
                    inProgress.toggle()
                    MatrixManager().login(server : "matrix.org",
                                          username : "{UserName}",
                                          password : "{Password}") {  id , error in
                        if let deviceId = id {
                            self.deviceId = deviceId
                        }
                        inProgress.toggle()

                    }
                }
            },label: {
                if(inProgress){
                    ProgressView()
                }else{
                   Text("Check SDK")
                }
            })
            
            Text(deviceId ?? "null")
                .padding(.top,10)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
