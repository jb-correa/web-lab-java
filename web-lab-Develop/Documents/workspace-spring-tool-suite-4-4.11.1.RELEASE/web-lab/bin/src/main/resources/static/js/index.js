//array para guardar las prácticas que ya existen
let elegidas = [];
let cantidad = 1;

const agregarPractica = () => {

    if (cantidad <= 3) {
        //selecciono input
        const valor = document.getElementById('selectPractica').value;

        //si el input esta vacío, salgo de la función
        if (!valor) return;

        if (elegidas.includes(valor)) {
            alert(`La práctica ${valor} ya fue seleccionada.`);
            return;
        }

        //busco la opción con el valor del input
        const opcion = document.querySelector('option[value="' + valor + '"]');

        //si la opción no existe, salgo
        if (opcion == undefined) {
            alert("El código no existe");
            return;
        }

        //si pasa las validaciones, la agrego al array
        elegidas.push(valor)

        //desabilito la opción de la lista
        opcion.disabled = true;

        //limpio el input
        document.getElementById("selectPractica").value = ""

        //creo el item
        const item = document.createElement("li");
        item.innerHTML = `
        <div>
        <p>${opcion.label} (code: ${valor})</p>
        <label>Resultado:</label>
        <input name="resultado${cantidad}" type="text">
        <br>
        <br>
        <button id="borrar${valor}">x</button>
        </div>`;

        //se incrementa la cantidad
        cantidad++;

        //lo agrego en la lista
        document.getElementById('practicas-elegidas').appendChild(item);

        //presto atención a los botones para borrar
        const botonEliminar = document.getElementById(`borrar${valor}`)
        botonEliminar.addEventListener("click", (e) => {
            e.target.parentElement.parentElement.remove()
            elegidas = elegidas.filter(e => e !== valor)
            opcion.disabled = false;
            cantidad--;
        })
    } else {
        alert("No se pueden agregar más de prácticas")
    }

}

//presto atención al botón para agregar
//const botonAgregar = document.getElementById("boton-agregar")
//botonAgregar.addEventListener("click", agregarPractica)

//Enviar form con enter
document.getElementById('buscarPaciente')
    .addEventListener('keyup', function (event) {
        if (event.code === 'Enter') {
            event.preventDefault();
            document.getElementById('buscarPacienteInput').submit();
        }
    });


