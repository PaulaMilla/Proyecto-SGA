export interface Rol {
  id: number;
  nombre: string;
}

export interface Usuario {
  id: number; // opcional en POST
  nombre: string;
  apellido: string;
  correo: string;
  estado: string;
  fechaCreacion?: Date;
  fechaModificacion?: Date;
  fechaUltimoAcceso?: Date;
  password: string;
  rol: Rol;
  nombre_farmacia: string;
}
