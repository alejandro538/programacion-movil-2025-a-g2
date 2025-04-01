import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/react';
import GestionPersonal from '../components/GestionPersonal';

const Paciente: React.FC = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Gestión de Pacientes</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <GestionPersonal
          title="Paciente"
          extraFields={{ "Historia clínica": '', "Tipo de afiliación": '' }}
        />
      </IonContent>
    </IonPage>
  );
};

export default Paciente;
