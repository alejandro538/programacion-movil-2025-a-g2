import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar, IonCard, IonCardHeader, IonCardTitle, IonCardContent } from '@ionic/react';
import './Home.css';

const Home: React.FC = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Gestión de Personal</IonTitle>
        </IonToolbar>
      </IonHeader>
      
      <IonContent className="home-content">
        <IonCard>
          <IonCardHeader>
            <IonCardTitle>Bienvenido</IonCardTitle>
          </IonCardHeader>
          <IonCardContent>
            Esta es la pantalla de inicio del sistema.
          </IonCardContent>
        </IonCard>
      </IonContent>
    </IonPage>
  );
};

export default Home;
