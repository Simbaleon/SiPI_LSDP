import {observer} from "mobx-react-lite";
import s from "./Content.module.css";

const HomePage = observer(() => {
    return (
        <div style={{height: '100%', width: '90%', margin: 'auto'}}>
            <div className={s.homeH1}>
                <h1>Новая платформа для фрилансеров со всей России <b>Freelance-platform</b>!</h1>
            </div>
            <div className={s.textCard}>
                Здесь вы всегда сможете найти мастера, который с лёгкостью выполнит любое ваше задание, или же работодателя, у которого всегда найдётся для вас работа.
            </div>
            <div className={s.textCard}>
                Улучшайте свои навыки, развивайте ваши профессиональные качества, и мы будем развиваться вместе с вами!
            </div>
        </div>
    )
})

export default HomePage;