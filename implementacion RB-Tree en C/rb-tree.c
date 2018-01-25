#include <stdlib.h>
#include <stdio.h>


typedef struct Nodo{
	struct Nodo* p;
	struct Nodo* l;
	struct Nodo* r;
	int valor; //lo que se almacena
	int llave; //valor a comparar
	unsigned char c; //0 es negro, otra cosa es rojo.
} nodo;


void left_rotate(nodo** t, nodo* x){
	nodo* y = x->r;
	x->r = y->l;
	if(y->l != NULL){
		(y->l)->p = x;
	}
	y->p = x->p;
	if(x->p == NULL){
		t = &y;
	}
	else{
		if(x == (x->p)->l){
			(x->p)->l = y;
		}
		else{
			(x->p)->r = y;	
		}
	}
	y->l = x;
	x->p = y;
}

void right_rotate(nodo** t,nodo *y){
	nodo* x = y->l;
	y->l = x->r;
	if(x->r != NULL){
		(x->r)->p = y;
	}
	x->p = y->p;
	if(y->p == NULL){
		t = &x;
	}
	else{
		if(y == (y->p)->l){
			(y->p)->l = x;
		}
		else{
			(y->p)->r = x;
		}
	}
	x->r = y;
	y->p = x;
}

void rb_insert_fixup(nodo** t,nodo*z){
	while((z->p)->c != 0){
		if(z->p == ((z->p)->p)->l){
			nodo* y = ((z->p)->p)->r;
			if(y->c != 0){
				(z->p)->c = 0;
				y->c = 0;
				((z->p)->p)->c = 1;
				z = (z->p)->p;
			}
			else{
				if(z == (z->p)->r){
					z = z->p;
					left_rotate(t,z);
				}
				(z->p)->c = 0;
				((z->p)->p)->c = 1;
				right_rotate(t,(z->p)->p);
			}
		}
		else{
			nodo* y = ((z->p)->p)->l;
			if(y->c != 0){
				(z->p)->c = 0;
				y->c = 0;
				((z->p)->p)->c = 1;
				z = (z->p)->p;
			}
			else{
				if(z == (z->p)->l){
					z = z->p;
					left_rotate(t,z);
				}
				(z->p)->c = 0;
				((z->p)->p)->c = 1;
				right_rotate(t,(z->p)->p);
			}
		}
	}
}

void rb_insert(nodo** t,nodo* z){
	nodo* y = NULL;
	nodo* x = *t;
	while(x != NULL){
		y = x;
		if (z->llave < x->llave){
			x = x->l;
		}
		else{
			x = x->r;
		}
	}
	z->p = y;
	if(y== NULL){
		t = &z;
	}
	else{
		if(z->llave < y->llave){
			y->l = z;
		}
		else{
			y->r = z;
		}
	}
	z->l = NULL;
	z->r = NULL;
	z->c = 1;
	rb_insert_fixup(t,z);
}

nodo* rb_search(nodo** t, int k){
	nodo *x = *t;
	while(x!=NULL && k != x->llave){
		if(k < x->llave) x = x->l;
		else x= x->r;
	}
	return x;
}

void rb_transplant(nodo** t,nodo* u,nodo* v){
	if(u->p == NULL){
		t = &v;
	}
	else{
		if(u == (u->p)->l){
			(u->p)->l = v;
		}
		else{
			(u->p)->r = v;
		}
	}
	v->p = u->p;
}

nodo* tree_minimo(nodo** t){
	return NULL;
}

void rb_delete_fixup(nodo** t, nodo* x){
	;
}

void rb_delete(nodo **t,nodo *z){
	nodo* y = z;
	nodo* x;
	unsigned char color_original_y = y->c;
	if(z->l == NULL){
		x = z->r;
	}	
	else{
		if(z->r == NULL){
			x = z->l;
		}
		else{
			y = tree_minimo(&(z->r));
			color_original_y = y->c;
			x = y->r;
			if(y->p == z){
				x->p = z;
			}
			else{
				rb_transplant(t,y,y->r);
				y->r = z->r;
				(y->r)->p = y;
			}
			rb_transplant(t,z,y);
			y->l=z->l;
			y->c = z->c;
		}
	}
	if(color_original_y == 0){
		rb_delete_fixup(t,x);
	}
}
//argc indica el número de argumentos
//argv es un arreglo de cadenas con los
//argumentos. Como String[] args en Java
int main(int argc, char** argv){
	if(argc > 1){
		const int tam_array = argc-1;
		int* array = (int*) malloc(sizeof(int)*tam_array);
		int i;
		for(i = 1;i<argc;i++){
			array[i-1] = atoi(argv[i]);
			}
		nodo **raiz = (nodo**)malloc(sizeof(nodo*));
		nodo* inicial = (nodo*)malloc(sizeof(nodo));
		inicial -> c = 0;
		(*inicial).valor = array[0];
		inicial -> llave = inicial -> valor;
		inicial -> p = inicial -> r = inicial -> l = NULL;

		raiz = &inicial;
		
		nodo* uno = (nodo*)malloc(sizeof(nodo));
		uno -> c = 0;
		(*uno).valor = array[1];
		uno -> llave = uno -> valor;
		uno -> p = uno -> r = uno -> l = NULL;

		rb_insert(raiz,uno);
		printf("%u\n", rb_search(raiz,3)!=NULL);

		free(inicial);
		free(uno);
		
		free(array);
	}
	return 0;
}