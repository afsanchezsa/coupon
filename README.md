# Implementación de Recomendación de Ítems Dado un Valor de Cupón

## Algoritmo (Resumido)

El algoritmo implementado se basa en el **algoritmo de mochila 0/1 (knapsack)** con programación dinámica, que permite hallar el conjunto óptimo de ítems que maximice el gasto sin sobrepasar el valor del cupón dado.

### Problema y Complejidad

El algoritmo tiene una complejidad de \(O(\text{valor del cupón} \times \text{número de ítems favoritos})\). Sin embargo, existen dos desafíos principales:

1. **Valor del cupón como flotante**:
    - Una de las dimensiones de la matriz de programación dinámica no puede acotarse fácilmente, generando un uso excesivo de memoria.

2. **Precios de gran magnitud**:
    - Por ejemplo, en el dataset con precios de los ítems `"MLA1448885343"` y `"MLA1448885331"`, algunos precios sobrepasan los millones de pesos.

### Optimización 1: Factor de Precisión

Para reducir el consumo de memoria, se introduce el concepto de **factor de precisión** (`item.service.precision`). Esto permite mapear el problema a enteros más pequeños, reduciendo la dimensionalidad de la matriz de programación dinámica.

#### Ejemplo
- Ítems favoritos y cupón inicial:
  ```json
  [
    { "item_id": "MELI1", "price": 100.0 },
    { "item_id": "MELI2", "price": 210.0 },
    { "item_id": "MELI3", "price": 260.0 },
    { "item_id": "MELI4", "price": 80.0 },
    { "item_id": "MELI5", "price": 90.0 }
  ]
  Cupón: 500.0


Respuesta óptima: ["MELI1", "MELI2", "MELI4", "MELI5"] (dimensión de la matriz: 500x5).

Aplicando un factor de precisión de 10:

Precios transformados:
  
        [
        { "item_id": "MELI1", "price": 10 },
        { "item_id": "MELI2", "price": 21 },
        { "item_id": "MELI3", "price": 26 },
        { "item_id": "MELI4", "price": 8 },
        { "item_id": "MELI5", "price": 9 }
        ]

Cupón transformado: ceil(500 / 10) = 50.
Dimensión de la matriz: 50x5 (diez veces menos memoria)

Impacto:
Aunque esta optimización reduce significativamente el uso de memoria, puede disminuir la precisión de los resultados.


### Optimización 2: Compensación Residual
Para mitigar el margen de error introducido por la Optimización 1, se calcula el residuo de la aproximación de los precios y se compara con un parámetro denominado umbral del dolor (item.service.pain.limit).

Ejemplo
Precios originales: [0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9] (11 ítems).

Cupón inicial: 10.

Aplicando la Optimización 1 (factor de precisión = 1):

Precios aproximados: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1].
Respuesta: 10 ítems comprados.

Aplicando la Optimización 2:

Residuos: Cada ítem tiene un residuo de 0.1, sumando un total de 1.1.
Nuevo cupón: floor(1.1 / 1) + 10 = 11.
Respuesta ajustada: 11 ítems comprados.




## Algoritmo (Explicacion detallada)

El algoritmo propuesto de basa en el algoritmo knapsack 0/1 (no fraccional) con matriz de programacion dinamica que permite
hallar el conjunto de items mas optimo para maximizar el gasto sin sobrepasar el valor del cupon dado.
Dado que este algoritmo tiene una complejidad de O(valor del cupon*numero de items favoritos) nos encontramos ante un problema
dado que el valor del cupon es flotante lo que en primer lugar daria paso a que una de las dimensiones de la matriz de programacion
dinamica no se pueda acotar por lo tanto el algoritmo propuesto haria un uso excesivo de memoria. Ademas, con una inspeccion
superficial del dataset con los precios de los valores "MLA1448885343" y "MLA1448885331" notamos que los precios tambien son
muy grandes en cuestion de unidades (sobrepasando en ocasiones los millones de pesos por ejemplo). Por ello como optimizacion se
introduce el concepto de factor de precision que a nivel de codigo puede encontrarse como item.service.precision. esta propiedad permite
mapear nuestro problema a un entero y reducir la dimensionalidad del cupon (y en consecuencia de los precios de los productos) esto
con el fin de administrar de mejor forma la memoria (esto desde luego puede afectar la precision de la respuesta pero gracias a la segunda
optimizacion denominada compensacion residual disminuimos el margen de error) pues este es un requerimiento esencial dado que un
usuario puede tener miles de items favoritos y no tenemos tampoco una restriccion clara del valor del cupon por lo que no controlar
la dimensionalidad de la matriz de programacion dinamica puede llevar a errores de desborde de la memoria. Expliquemos esta
primera optimizacion con un ejemplo: suponga que tenemos unos items favoritos descritos como {item_id='MELI1',price:100.0},
{item_id='MELI2',price:210.0}, {item_id='MELI3',price:260}, {item_id='MELI4',price:80},{item_id='MELI5',price:90} y ademas este usuario cuenta con un
cupon de 500.0 . Dado que la respuesta optima es  (MELI1,MELI2,'MELI4', MELI5) en este caso la matriz de programacion dinamica usada tuvo
unas dimensiones de (500 x 5 ). supongamos que tenemos un factor de presicion dado por item.service.precision=10. entonces voy a tomar el precio
de cada item y lo voy a dividir  por este factor teniendo ahora este escenario:
{item_id='MELI1',price:10.0},
{item_id='MELI2',price:21.0}, {item_id='MELI3',price:26.0}, {item_id='MELI4',price:8.0},{item_id='MELI5',price:9} . ahora voy a aproximar estos precios a su valor entero siguiente superior aplicando la funcion ceil,
entonces tenemos este escenario:
{item_id='MELI1',price:10.0},
{item_id='MELI2',price:21.0}, {item_id='MELI3',price:26.0}, {item_id='MELI4',price:8.0},{item_id='MELI5',price:9.0}. lo hacemos de la misma forma con el valor del cupon el cual queraria en ceil(500/10)=50

ahora ante este escenario la respuesta correcta cual seria ? la misma:  (MELI1,MELI2,'MELI4', MELI5), pero en este caso solucionamos el problema con
una matriz de programacion dinamica de dimension (50x5) (note que es diez veces menos memoria que la que se uso para
solucionar el problema sin el factor de precision). Sin embargo aplicar esta optimizacion no siempre nos va a llevar a la respuesta mas optima.
para reducir el margen de error creamos una segunda optimizacion, la cual consiste en tomar los residuos que nos produce la aproximacion de
cada precio y sumarlos, una vez que tenemos el valor del residuo lo comparamos con otro parametro que llamaremos el umbral del dolor (llamado asi la cantidad de dinero que nos
quita la optimizacion 1 que nos generaria un dolor) y que en codigo se referencia como: item.service.pain.limit. En caso de que la suma
de los residuos exceda ese umbral del dolor vamos a sumarle al cupon original el valor floor(residuo/factor de precicion) y ejecutariamos el algoritmo de knapsack con normalidad reduciendo el margen de error.

veamoslo con un ejemplo. suponga que tenemos los items favoritos que tienen los precios de (0.9,0.9,0.9,0.9,0.9,0.9,0.9,0.9,0.9,0.9,0.9)
es decir 11 items que cuestan 0.9. realizando la optimizacion 1 asumiendo que el factor de precision es 1  vamos a obtener los siguientes precios (1,1,1,1,1,1,1,1,1,1)
ahora supongamos que nuestro cupon original tiene un valor de 10. cual seria la respuesta del algoritmo con la optimizacion 1 ? seria que podemos comprar solo 10 items, pero
si observamos podemos apreciar que con el cupon de 10 podriamos comprar los 11 items que cuestan 9.9. entonces, al aplicar la optimizacion
1 perdimos precision en nuestra respuesta. ahora apliquemos la optimizacion 2 : calculemos los residuos: vemos que como cada precio de 0.9 se
aproximo a 1 el residuo de cada uno fue 0.1 y como son 11 items el residuo total fue de 1.1 . aplicamos la funcion floor(1.1/1)=1 y le sumamos al cupon
original este valor entonces nuestro cupon nuevo va a tener un valor de 10+1=11. si ejecutamos nuestro algoritmo de knapsack con cupon=11 y precio de los items (1,1,1,1,1,1,1,1,1,1,1)
vemos que la respuesta sera que podemos comprar los 11 items tal como la respuesta optima!
Estas optimizaciones fueron plasmadas a nivel de codigo en la presente solucion.




pasos para compilar la aplicacion
1. genere el jar de la aplicacion con cualquiera de estos comandos
   ./mvnw clean package o mvn clean package

2. si cuenta con docker-compose instalado puede ejecutar el comando

docker-compose up
3. si no cuenta con docker compose instalado  ejecute el comando

java -jar target/app-0.0.1-SNAPSHOT.jar


podra entonces probar el api con el comando
curl --location 'http://127.0.0.1:8080/item/coupon' \
--header 'Content-Type: application/json' \
--data '{
"item_ids":["MLA1448885331","MLA1448885343","MLA1448885332"],
"amount":5000000.0
}'

La implementacion de esta asignacion se asigna con las mejores practicas como:
uso de programacion orientada aspectos para el aspecto de logging (con un trace id administrado por un bean con scope request)
uso de tipos genericos para la deserializacion de clases customizadas almacenadas en redis.
uso de parallel stream para hacer consultas concurrentes al api con el fin de optimizar el tiempo de respuesta
soporte de cache en redis para disminuir el tiempo de respuesta y evitar errores de rate limit
uso de optionals para el manejo de errores.
implementacion de arquitectura hexagonal que permite reusar el codigo de la logica del negocio en cualquier contexto con flexibilidad de su infraestructura
test unitarios y de integracion
flexibilidad dada por dependencias de las abstracciones y no de las implementaciones (note que el ItemAPIClient tiene dos implementaciones distintas que pueden cambiar
con solo ajustar un parametro en un qualifier).



