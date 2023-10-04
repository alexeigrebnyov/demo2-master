let dateDatafrom = document.getElementById("dateData1");
let dateDatato = document.getElementById("dateData2");
let lotData = document.getElementById("lotData");
let testData = document.getElementById("testData");
let dateFromDiv = document.getElementById("dateFromDiv");


function getInUse() {
    let  uses=document.getElementById("inuser")
    let opt = ''
    fetch("/qc/getInUse")
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
    fetch("/qc/"+dateFrom).then((res) => res.json())
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

function getChartByTestandLot(end='', name='', from='', to=''){

    dataList=[]
    plot_labels=[]
    controls=[]
    fetch('/achtv/data',
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: end
        })
        .then((res)=> res.json()
            .then((data)=>{
                total_val=[]
                cntrl_val=[]
                total_dates=[]
                cntrl_dates=[]
                data.forEach( function (some) {
                    // let h = (some.higher/some.total).toFixed(3)
                        let d = some.dateCons

                        total_val.push(some.higher / some.total).toFixed(3)
                        total_dates.push(d)
                        if (d >= from && d <= to) {
                            cntrl_val.push(some.higher / some.total).toFixed(3)
                            cntrl_dates.push(d)
                        }

                    })
                // console.log(cntrl_val)
                // console.log(cntrl_dates)
                // console.log(from)
                // console.log(to)
                    dataList.push(
                        {
                            x: total_dates,
                            y: total_val,
                            mode: 'markers',
                            marker: {
                                size: 15
                            },
                            type: 'scatter',
                            name: 'k'
                        }
                    )
                //
                // }
                // // total_val= total_val
                // let sliced = total_val.slice(total_dates.indexOf('2022-11-29'), total_dates.indexOf('2023-01-03'))
                // let datesliced = total_dates.slice(total_dates.indexOf('2022-11-29'), total_dates.indexOf('2023-01-03'))
                // console.log(total_dates.indexOf('2022-11-29'))
                // console.log(total_dates.indexOf('2023-01-03'))
                // // total_dates= total_dates
                let mean = cntrl_val.reduce((acc, curr)=>{
                    return acc + curr
                }, 0) / cntrl_val.length;
                let mean_range = total_val.map((k)=>{
                    return mean
                })
                // console.log(mean)
                // console.log(mean_range)
                let s_1=total_val.map((k)=>{
                    return mean-dev(cntrl_val)
                })
                let s_2=total_val.map((k)=>{
                    return mean-2*dev(cntrl_val)
                })
                let s_3=total_val.map((k)=>{
                    return mean-3*dev(cntrl_val)
                })
                let spl1=total_val.map((k)=>{
                    return mean+dev(cntrl_val)
                })
                let spl2=total_val.map((k)=>{
                    return mean+2*dev(cntrl_val)
                })
                let spl3=total_val.map((k)=>{
                    return mean+3*dev(cntrl_val)
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
                // // dataList.push(getControlNumbers(lot, test, date))
                // // test_info= test.length>0?' AT/AgHIV'+test:''
                layout = {
                    title: 'Доля высоких результатов АЧТВ за текущую дату '+name,
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
                TESTER = document.getElementById("graph");
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
    fetch("/qc/saveLoadedMeasures").then()
    getInUse()
    setTimeout(
    getFirst, 1000)
}




