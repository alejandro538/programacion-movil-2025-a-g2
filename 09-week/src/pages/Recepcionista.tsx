import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/react';
import GestionPersonal from '../components/GestionPersonal';

const Recepcionista: React.FC = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Gestión de Recepcionistas</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <GestionPersonal
          title="Recepcionista"
          extraFields={{ "Horario laboral": '', "Extensión telefónica": '' }}
        />
      </IonContent>
    </IonPage>
  );
};

export default Recepcionista;
