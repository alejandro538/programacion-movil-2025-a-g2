import { Redirect, Route } from 'react-router-dom';
import { 
  IonApp, 
  IonRouterOutlet, 
  setupIonicReact, 
  IonTabBar, 
  IonTabButton, 
  IonLabel, 
  IonIcon, 
  IonTabs 
} from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { people, person, medkit, business } from 'ionicons/icons';
import Home from './pages/Home';
import Medico from './pages/Medico';
import Enfermero from './pages/Enfermero';
import Recepcionista from './pages/Recepcionista';
import Paciente from './pages/Paciente';

import './pages/Home.css';  

import '@ionic/react/css/core.css';
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';
import '@ionic/react/css/palettes/dark.system.css';
import './theme/variables.css';

setupIonicReact();

const App: React.FC = () => (
  <IonApp>
    <IonReactRouter>
      <IonTabs>
        <IonRouterOutlet>
          <Route exact path="/home" component={Home} />
          <Route exact path="/medico" component={Medico} />
          <Route exact path="/enfermero" component={Enfermero} />
          <Route exact path="/recepcionista" component={Recepcionista} />
          <Route exact path="/paciente" component={Paciente} />
          <Route exact path="/">
            <Redirect to="/home" />
          </Route>
        </IonRouterOutlet>

        <IonTabBar slot="bottom">
          <IonTabButton tab="medico" href="/medico">
            <IonIcon icon={medkit} />
            <IonLabel>Médico</IonLabel>
          </IonTabButton>
          <IonTabButton tab="enfermero" href="/enfermero">
            <IonIcon icon={person} />
            <IonLabel>Enfermero</IonLabel>
          </IonTabButton>
          <IonTabButton tab="recepcionista" href="/recepcionista">
            <IonIcon icon={business} />
            <IonLabel>Recepcionista</IonLabel>
          </IonTabButton>
          <IonTabButton tab="paciente" href="/paciente">
            <IonIcon icon={people} />
            <IonLabel>Paciente</IonLabel>
          </IonTabButton>
        </IonTabBar>
      </IonTabs>
    </IonReactRouter>
  </IonApp>
);

export default App;
