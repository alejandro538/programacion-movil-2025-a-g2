import React, { useState } from 'react';
import { IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonButton, IonInput, IonItem, IonLabel, IonList } from '@ionic/react';

interface GestionPersonalProps {
  title: string;
  extraFields: { [key: string]: string };
}

const GestionPersonal: React.FC<GestionPersonalProps> = ({ title, extraFields }) => {
  const [formData, setFormData] = useState<{ [key: string]: string }>({
    nombre: '',
    apellido: '',
    edad: '',
    correo: '',
    ...extraFields
  });

  const [users, setUsers] = useState<any[]>([]);
  const [editingIndex, setEditingIndex] = useState<number | null>(null);

  const handleChange = (field: string, value: string) => {
    setFormData({ ...formData, [field]: value });
  };

  const handleAction = (action: string) => {
    switch (action) {
      case "Agregar":
        if (editingIndex === null) {
          setUsers([...users, { ...formData }]);
        } else {
          const updatedUsers = [...users];
          updatedUsers[editingIndex] = { ...formData };
          setUsers(updatedUsers);
          setEditingIndex(null);
        }
        setFormData({ nombre: '', apellido: '', edad: '', correo: '', ...extraFields });
        break;
      case "Modificar":
        const userToEdit = users.findIndex(user => user.correo === formData.correo);
        if (userToEdit !== -1) {
          setFormData(users[userToEdit]);
          setEditingIndex(userToEdit);
        }
        break;
      case "Eliminar":
        setUsers(users.filter(user => user.correo !== formData.correo));
        break;
      case "Consultar":
        console.log("Usuarios:", users);
        break;
      default:
        console.log("Acción no válida");
    }
  };

  return (
    <IonCard>
      <IonCardHeader>
        <IonCardTitle>{title}</IonCardTitle>
      </IonCardHeader>
      <IonCardContent>
        <IonList>
          <IonItem>
            <IonLabel position="stacked">Nombre</IonLabel>
            <IonInput value={formData.nombre} onIonChange={e => handleChange("nombre", e.detail.value!)} />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Apellido</IonLabel>
            <IonInput value={formData.apellido} onIonChange={e => handleChange("apellido", e.detail.value!)} />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Edad</IonLabel>
            <IonInput type="number" value={formData.edad} onIonChange={e => handleChange("edad", e.detail.value!)} />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Correo Electrónico</IonLabel>
            <IonInput type="email" value={formData.correo} onIonChange={e => handleChange("correo", e.detail.value!)} />
          </IonItem>
          {Object.keys(extraFields).map(field => (
            <IonItem key={field}>
              <IonLabel position="stacked">{field}</IonLabel>
              <IonInput value={formData[field]} onIonChange={e => handleChange(field, e.detail.value!)} />
            </IonItem>
          ))}
        </IonList>
        
        <IonButton expand="full" color="primary" onClick={() => handleAction("Agregar")}>Agregar</IonButton>
        <IonButton expand="full" color="secondary" onClick={() => handleAction("Modificar")}>Modificar</IonButton>
        <IonButton expand="full" color="danger" onClick={() => handleAction("Eliminar")}>Eliminar</IonButton>
        <IonButton expand="full" color="success" onClick={() => handleAction("Consultar")}>Consultar</IonButton>
      </IonCardContent>
      
      {users.length > 0 && (
        <IonCard>
          <IonCardHeader>
            <IonCardTitle>Usuarios Registrados</IonCardTitle>
          </IonCardHeader>
          <IonCardContent>
            {users.map((user, index) => (
              <IonItem key={index}>
                <IonLabel>{user.nombre} {user.apellido} - {user.correo}</IonLabel>
              </IonItem>
            ))}
          </IonCardContent>
        </IonCard>
      )}
    </IonCard>
  );
};

export default GestionPersonal;