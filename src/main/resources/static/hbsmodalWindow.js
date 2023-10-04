

function initLotModalValues(end='', second='', third='') {
    fetch("/hbsqc/"+end).then((res) => res.json())
        .then((data) => {


                let serdiv = document.getElementById("seriyadiv1");
                let nomerdiv = document.getElementById("nomerdiv1");
                let kemdiv = document.getElementById("kemdiv1");
                let kogdadiv = document.getElementById("kogdadiv1");
                let nomdiv = document.getElementById("nomdiv1");
                let prenomdiv = document.getElementById("prenomdiv1");
                let saverdiv = document.getElementById("saver");

                        serdiv.innerHTML=` <label for="name">Название</label>
                            <input class="form-control" type="text" name="name"  id="name"  value="${data.name}">`

                        nomerdiv.innerHTML=`<label for="lot">Серия</label>
                                <div>
                                    <input class="form-control" type="text" name="lot" id="lot" value="${data.lot}" 
                                    onchange="updateLotModalValues()">
                                </div>`
                        kemdiv.innerHTML=`<label for="dateFromModal">Дата с</label>
                                <div>
                                    <input class="form-control" type="datetime-local" id="dateFromModal" name="dateFromModal" value="${data.dateFrom}">
                                </div>`
                        kogdadiv.innerHTML=`<label for="dateToModal">Срок годности</label>
                                <div>
                                    <input class="form-control" type="datetime-local" name="dateToModal" id="dateToModal" value="${data.dateTo}" >
                                </div>`
                        nomdiv.innerHTML=`<label for="number">Коробка №</label>
                                <div>
                                    <input class="form-control" type="number" name="number" id="number" value="${data.number+1}" >
                                </div>`
                        prenomdiv.innerHTML=`<label for="inuse">В работе </label>
                                <input class="form-control" type="text" name="inuse" id="inuse" size="12" value="${data.inuse}">`
                        saverdiv.innerHTML=`<input id="saverInput" type="hidden" value="${second}">
                                               <input type="hidden" id="updateInput" value="${third}">`
})
}
function updateLotModalValues(end='') {
    fetch("/hbsqc/"+document.getElementById("updateInput").value+document.getElementById("lot").value).then((res) => res.json())
        .then((data) => {


            let serdiv = document.getElementById("seriyadiv1");
            let nomerdiv = document.getElementById("nomerdiv1");
            let kemdiv = document.getElementById("kemdiv1");
            let kogdadiv = document.getElementById("kogdadiv1");
            let nomdiv = document.getElementById("nomdiv1");
            let prenomdiv = document.getElementById("prenomdiv1");

            kemdiv.innerHTML=`<label for="dateFromModal">Дата с</label>
                                <div>
                                    <input class="form-control" type="datetime-local" id="dateFromModal" name="dateFromModal" value="${data.dateFrom}">
                                </div>`
            kogdadiv.innerHTML=`<label for="dateToModal">Срок годности</label>
                                <div>
                                    <input class="form-control" type="datetime-local" name="dateToModal" id="dateToModal" value="${data.dateTo}" >
                                </div>`
            nomdiv.innerHTML=`<label for="number">Коробка №</label>
                                <div>
                                    <input class="form-control" type="number" name="number" id="number" value="${data.number+1}" >
                                </div>`
            prenomdiv.innerHTML=`<label for="inuse">В работе </label>
                                <input class="form-control" type="text" name="inuse" id="inuse" size="12" value="${data.inuse}">`
        })
}

function saveLotTester() {
    fetch("/hbsqc/"+document.getElementById("saverInput").value,
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: JSON.stringify({
                "name": document.getElementById("name").value,
                "lot": document.getElementById("lot").value,
                "dateFrom": document.getElementById("dateFromModal").value.replace('T', ' '),
                "dateTo": document.getElementById("dateToModal").value.replace('T', ' '),
                "number": document.getElementById("number").value,
                "measureList": null,
                "inuse": document.getElementById("inuse").value
            })

        }
    )
    $('jsModalBox_lot').clos
}