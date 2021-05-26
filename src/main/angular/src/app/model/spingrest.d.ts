import { SpringPageInfo } from './sping-page-info';
import { Register } from './register';


export interface SpringRestApi {
    _embedded: { [key: string]: Register[] };
    page: SpringPageInfo;
}