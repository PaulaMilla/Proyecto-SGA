export interface Rol {
  id: number;
  nombre: string;
}

export interface Usuario {
  id: number;
  nombre: string;
  apellido: string;
  correo: string;
  estado: string;
  nombreFarmacia: string;
  nombreRol: string;
  fechaCreacion?: Date;
  fechaModificacion?: Date;
  idRol: number;
}

export interface UsuarioRequestDTO {
  nombre: string;
  apellido: string;
  correo: string;
  password: string;
  estado: string;
  nombre_farmacia: string;
  idRol: number;
}

export interface UsuarioUpdateDTO {
  id: number;
  estado: string;
  idRol: number;
}


