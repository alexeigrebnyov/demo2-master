let dateDatafrom = document.getElementById("dateData1");
let dateDatato = document.getElementById("dateData2");
let lotData = document.getElementById("lotData");
let testData = document.getElementById("testData");
let dateFromDiv = document.getElementById("dateFromDiv");


function getInUse() {
    let  uses=document.getElementById("inuser")
    let opt = ''
    fetch("/hbsqc/getInUse")
        .then((res)=>res.json())
        .then((data)=>{
            uses.innerHTML=` 
                         <input type="hidden" id="lotseries" value="${data[0]}">
                         <input type="hidden" id="testseries" value="${data[1]}">
                         <input type="hidden" id="lotname" value="${data[2]}">
                         <input type="hidden" id="testname" value="${data[3]}">
                        `
            // console.log('data '+data)
            // console.log('usesVals '+uses)

            }
        )
}
getInUse()
function hideSlicer() {
    $( '#slicelabel' ).hide()
    $( '#inputSlice' ).hide()

}
hideSlicer()
// console.log('uses '+uses)
function getDataLists(dateFrom='', skip=true) {
    // let dateDatafrom = document.getElementById("dateData1");
    // let dateDatato = document.getElementById("dateData2");
    // let lotData = document.getElementById("lotData");
    // let testData = document.getElementById("tesData");
    // let dateFromDiv = document.getElementById("dateFromDiv");
    let dateDatafromval=''
    let lot=''
    let test=''
    let dateDatatoval=''
    let set = new Set()
    let testSet = new Set()
    fetch("/hbsqc/"+dateFrom).then((res) => res.json())
        .then((data) => {

            data.forEach(function (some) {
                
                dateDatafromval+=`<option value="${some.measure_date}" ></option>`
                set.add(some.lot)
                testSet.add(some.test)

            });
            setIterator=set.values()
            testIterator=testSet.values()

                for (let i = 0; i < set.size; i++) {
                    lot += `<option value="` + setIterator.next().value + `"` + `></option>`

                }


                for (let i = 0; i < testSet.size; i++) {
                    test += `<option value="` + testIterator.next().value + `"` + `></option>`

                }


            // dateFromDiv.innerHTML=`<input id="dateFrom" type="text" list="dateData1">`
       dateDatafrom.innerHTML=dateDatafromval
            if (skip) {
                lotData.innerHTML = lot
            }
        testData.innerHTML=test
        })
    // if (document.getElementById("lotFrom").value===''||document.getElementById("testFrom").value==='') {
    //     document.getElementById("lotFrom").value=document.getElementById("lotseries").value
    //     document.getElementById("testFrom").value=document.getElementById("testseries").value
    //
    // }
}
// function test(test=''){
//     console.log(test)
// }
// function getElByname(name='') {
//
//     console.log(document.getElementsByName(name)[0].value)
//

function getChartByTestandLot(lot='', test='', date='', div_name='', slice=0){
    title=document.getElementById("testname").value+' серия'+test.replaceAll('/','')
    if (div_name==='tester') {
        $( '#slicelabel' ).show()
        $( '#inputSlice' ).show()
        title=''
    }

    dataList=[]
    plot_labels=[]
    controls=[]
    lot1=lot==='/'?'/test':lot
    fetch('/hbsqc/getmeasuremap'+lot1+test+date)
        .then((res)=> res.json()
            .then((data)=>{
                // console.log(data)
                mapTest=  new Map(Object.entries(data))
                // mapLot=  new Map(Object.entries(data.at(1)))
                iteratorTestkey=mapTest.keys()
                iteratorTestval=mapTest.values()
                total_val=[]
                total_dates=[]

                // console.log('iterator: '+iteratorTestkey)
                // while (!iterator.next().done) {
                //   k=iterator.next().value
                //     // console.log(iterator.next().value)
                //     l_1.push(k)
                //     l_2.push(map.get(k))
                // }

                for (let i = 0; i < mapTest.size; i++) {
                    l_dates=[]
                    l_vals=[]

                    k=iteratorTestkey.next().value

                    // console.log('k: '+k)
                    l_measure=mapTest.get(k)
                    // console.log('l_measure: '+l_measure)
                    plot_labels.push(k)
                    for (let z=0; z<l_measure.length; z++) {
                        f= parseFloat(l_measure.at(z).measure_val.replaceAll(',','.'))
                        d=l_measure.at(z).measure_date
                        l_dates.push(d)
                        l_vals.push(f)
                        total_val.push(f)
                        total_dates.push(d)
                        // console.log('l_measure.at(i): '+l_measure.at(z))
                    }

                    // total_val.push(l_vals)


                    dataList.push(
                        {
                            x: l_dates,
                            y: l_vals,
                            mode: 'markers',
                            marker: {
                                size: 15
                            },
                            type: 'scatter',
                            name: k
                        }
                    )

                }
                // total_val= total_val
                sliced=total_val.slice(slice)
                // total_dates= total_dates
                let mean = sliced.reduce((acc, curr)=>{
                    return acc + curr
                }, 0) / sliced.length;
                let mean_range = total_val.map((k)=>{
                    return mean
                })
                let s_1=total_val.map((k)=>{
                    return mean-dev(sliced)
                })
                let s_2=total_val.map((k)=>{
                    return mean-2*dev(sliced)
                })
                let s_3=total_val.map((k)=>{
                    return mean-3*dev(sliced)
                })
                let spl1=total_val.map((k)=>{
                    return mean+dev(sliced)
                })
                let spl2=total_val.map((k)=>{
                    return mean+2*dev(sliced)
                })
                let spl3=total_val.map((k)=>{
                    return mean+3*dev(sliced)
                })
                dataList.push(
                    {
                        x: total_dates,
                        y: mean_range,
                        mode: 'lines',
                        line: {
                            color: 'rgb(7, 35, 197)',
                            width: 3
                        },
                        type: 'scatter',
                        name: 'X'
                    },
                    {
                        x: total_dates,
                        y: s_1,
                        mode: 'lines',
                        line: {
                            color: 'rgb(68, 216, 0)',
                            width: 3
                        },
                        type: 'scatter',
                        name: '-S'
                    },
                    {
                        x: total_dates,
                        y: s_2,
                        mode: 'lines',
                        line: {
                            color: 'rgb(227, 212, 6)',
                            width: 3
                        },
                        type: 'scatter',
                        name: '-2S'
                    },
                    {
                        x: total_dates,
                        y: s_3,
                        mode: 'lines',
                        line: {
                            color: 'rgb(227, 35, 6)',
                            width: 3
                        },
                        type: 'scatter',
                        name: '-3S'
                    },
                    {
                        x: total_dates,
                        y: spl1,
                        mode: 'lines',
                        line: {
                            color: 'rgb(68, 216, 0)',
                            width: 3
                        },
                        type: 'scatter',
                        name: '+S'
                    },
                    {
                        x: total_dates,
                        y: spl2,
                        mode: 'lines',
                        line: {
                            color: 'rgb(227, 212, 6)',
                            width: 3
                        },
                        type: 'scatter',
                        name: '+2S'
                    },
                    {
                        x: total_dates,
                        y: spl3,
                        mode: 'lines',
                        line: {
                            color: 'rgb(227, 35, 6)',
                            width: 3
                        },
                        type: 'scatter',
                        name: '+3S'
                    }


                )
                // dataList.push(getControlNumbers(lot, test, date))
                // test_info= test.length>0?' AT/AgHIV'+test:''
                layout = {
                    title: document.getElementById("lotname").value+' серия '+lot.replaceAll('/','')+' '
                        +title,
                    legend: {
                        y: 1.0,
                        x: 0.0,
                        traceorder: 'normal',
                        font: {size: 16},
                        orientation: "h"

                        // yref: 'paper'
                    },
                    showlegend: false
                };
                TESTER = document.getElementById(div_name);
                Plotly.newPlot( TESTER, dataList ,layout, {
                    margin: { t: 50, l:50}, }, {scrollZoom: true}, {editable: true});
                // console.log(dataList)
                // console.log(iteratorTestkey)
            }))

}
function getChartByDateFrom(dateF='', skip=true, lot='', test='', date='', div_name='', slice=0) {
    getDataLists(dateF, skip)
    getChartByTestandLot(lot, test, date, div_name, slice)
}
function clearLists () {
    dateFromDiv.innerHTML=`<div class="d-flex flex-row">
                        <div style="font-weight: bold; border-bottom:solid darkblue; border-top: solid darkblue; border-left:solid darkblue;  background-color: bisque" >Фильтр</div>
                            <div class="p-2" style="border-bottom:solid darkblue; border-top: solid darkblue; background-color: bisque ">
                        <label for="dateFrom" style="vertical-align: top; float: top; display: block; font-weight: bold" >Дата с:</label>
                        <input  id="dateFrom" name="date" type="text" list="dateData1" size="10" onchange="getChartByDateFrom('getmeasurelistsByDateFrom/'+document.getElementById('dateFrom').value.replace(' ','T')+' '+
                            document.getElementById('dateTo').value.replace(' ','T'), true, '','','/date/'+$( '#dateFrom' ).val().replace(' ','T'), 'tester', document.getElementById('inputSlice').value)">
                            </div>
                            <div class="p-2"style="border-bottom:solid darkblue; border-top: solid darkblue; background-color: bisque ">
                        <label for="dateTo" style="vertical-align: top; float: top; display: block;font-weight: bold">Дата по:</label>
                        <input id="dateTo" name="date" type="text" list="dateData1" size="10"
                               onchange="getChartByDateFrom('getmeasurelistsByDateFrom/'+document.getElementById('dateFrom').value.replace(' ','T')+' '+
                            document.getElementById('dateTo').value.replace(' ','T'), true, '','','/date/'+$( '#dateFrom' ).val().replace(' ','T')
                            +' '+$( '#dateTo' ).val().replace(' ','T'), 'tester', document.getElementById('inputSlice').value)">
                            </div>
                        <div class="p-2" style="border-bottom:solid darkblue; border-top: solid darkblue; background-color: bisque ">
                        <label for="lotFrom" style="vertical-align: top; float: top; display: block;font-weight: bold">Контроль:</label>
                        <input id="lotFrom" type="text" list="lotData" size="10"
                               onchange="getChartByDateFrom('getmeasurelistsByLot/'+document.getElementById('lotFrom').value, false,'/'+$( '#lotFrom' ).val(),'','', 'tester', document.getElementById('inputSlice').value)">
                        </div>
                        <div class="p-2" style="border-bottom:solid darkblue; border-top: solid darkblue; border-right:solid darkblue; background-color: bisque ">
                        <label for="testFrom" style="vertical-align: top; float: top; display: block;font-weight: bold">Тест:</label>
                        <input id="testFrom" type="text" list="testData" size="10"
                               onchange="getChartByTestandLot('/'+$( '#lotFrom' ).val(),'/'+$( '#testFrom' ).val(),'', 'tester1', document.getElementById('inputSlice1').value)">
                        </div>
                            <div class="p-2 col-md-5"></div>
                            <div style="font-weight: bold; border-bottom:solid firebrick; border-top: solid firebrick; border-left:solid firebrick; background-color:palegoldenrod">Добавить</div>
                            <div class="p-2" style="border-bottom:solid firebrick; border-top: solid firebrick; background-color:palegoldenrod">
                                <input type="submit" data-target="#jsModalBox_lot" class="btn btn-primary" data-toggle="modal" name="modalopen" id="modalopen" value="Контроль"
                                       onclick="initLotModalValues('getLotinuse','savelot', 'getTopLot/')">
                            </div>
                            <div class="p-2" style="border-bottom:solid firebrick; border-top: solid firebrick; background-color:palegoldenrod">
                                <input type="submit" data-target="#jsModalBox_lot" class="btn btn-success" data-toggle="modal" name="modalopen" id="modaltestopen" value="Тест"
                                       onclick="initLotModalValues('getTestinuse', 'savetest', 'getTopTest/')">
                            </div>

                            <div id="addData" class="p-2 col-md-3" style="border-bottom:solid firebrick; border-top: solid firebrick; border-right:solid firebrick; background-color:palegoldenrod">
                                <button class="btn btn-info" onclick="addData()">Данные</button>
                            </div>

                        </div>`
    // getDataLists('getmeasurelistsByDateFrom/2023-02-04T00:00')
    getChartByDateFrom('getmeasurelistsByDateFrom/2021-08-04T00:00', true,'/'+document.getElementById("lotseries").value,
        '/'+document.getElementById("testseries").value, '','tester1',0)
}
function getFirst() {

    getChartByDateFrom('getmeasurelistsByDateFrom/2021-08-04T00:00', true,'/'+document.getElementById("lotseries").value,
        '/'+document.getElementById("testseries").value, '','tester1', 0)
}

function addData() {
    fetch("/hbsqc/saveLoadedMeasures").then()
    getInUse()
    setTimeout(
    getFirst, 1000)
}




setTimeout(
    getFirst, 1000)