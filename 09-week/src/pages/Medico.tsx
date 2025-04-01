import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/react';
import GestionPersonal from '../components/GestionPersonal';

const Medico: React.FC = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Gestión de Médicos</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <GestionPersonal
          title="Médico"
          extraFields={{ Especialidad: '', Licencia: '' }}
        />
      </IonContent>
    </IonPage>
  );
};

export default Medico;
