import './App.css';
import Header from "./components/common/header/Header";
import Content from "./components/common/content/Content";
import Footer from "./components/common/footer/Footer";
import {useContext} from "react";
import {Context} from "./index";

function App() {
    const {userStore} = useContext(Context)
    window.addEventListener("beforeunload", function(e){
        userStore.logout()
    }, false);
    return (
        <div className="App">
            <Header/>
            <Content/>
            {/*<Footer/>*/}
        </div>
    );
}

export default App;
