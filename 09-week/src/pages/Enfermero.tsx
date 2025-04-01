import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/react';
import GestionPersonal from '../components/GestionPersonal';

const Enfermero: React.FC = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Gestión de Enfermeros</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <GestionPersonal
          title="Enfermero"
          extraFields={{ "Turno asignado": '', "Área de atención": '' }}
        />
      </IonContent>
    </IonPage>
  );
};

export default Enfermero;
