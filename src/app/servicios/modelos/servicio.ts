import { Categoria } from "../../categorias/modelos/categoria";

export class Servicio {
    id!: number;
    nombre!: string;
    descripcion!: string;
    precio!: number;
    imagen!: string;
    fechaCreacion!: string; 
    disponible!: boolean;
    objCategoria: Categoria | null = null;
}