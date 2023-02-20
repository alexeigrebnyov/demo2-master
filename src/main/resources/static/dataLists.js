let dateDatafrom = document.getElementById("dateData1");
let dateDatato = document.getElementById("dateData2");
let lotData = document.getElementById("lotData");
let testData = document.getElementById("testData");
let dateFromDiv = document.getElementById("dateFromDiv");


function getDataLists(dateFrom='') {
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
                lot+=`<option value="`+setIterator.next().value+`"`+ `></option>`

            }
            for (let i = 0; i < testSet.size; i++) {
                test+=`<option value="`+testIterator.next().value+`"`+ `></option>`

            }

            // dateFromDiv.innerHTML=`<input id="dateFrom" type="text" list="dateData1">`
       dateDatafrom.innerHTML=dateDatafromval
       lotData.innerHTML=lot
        testData.innerHTML=test
        })
}function test(test=''){
    console.log(test)
}
function getElByname(name='') {

    console.log(document.getElementsByName(name)[0].value)

}
function getChartByTestandLot(lot='', test='', date='', div_name=''){
    dataList=[]

    plot_labels=[]
    controls=[]
    lot=lot==='/'?'/test':lot
    fetch('/qc/getmeasuremap'+lot+test+date)
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
                    console.log('l_measure: '+l_measure)
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
                total_val= total_val.slice(-0)
                total_dates= total_dates.slice(-0)
                let mean = total_val.reduce((acc, curr)=>{
                    return acc + curr
                }, 0) / total_val.length;
                let mean_range = total_val.map((k)=>{
                    return mean
                })
                let s_1=total_val.map((k)=>{
                    return mean-dev(total_val)
                })
                let s_2=total_val.map((k)=>{
                    return mean-2*dev(total_val)
                })
                let s_3=total_val.map((k)=>{
                    return mean-3*dev(total_val)
                })
                let spl1=total_val.map((k)=>{
                    return mean+dev(total_val)
                })
                let spl2=total_val.map((k)=>{
                    return mean+2*dev(total_val)
                })
                let spl3=total_val.map((k)=>{
                    return mean+3*dev(total_val)
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
                test_info= test.length>0?' AT/AgHIV'+test:''
                layout = {
                    title: 'ОП BioTest серия '+lot+test_info,
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
                console.log(dataList)
                // console.log(iteratorTestkey)
            }))

}
function getChartByDateFrom(dateF='', lot='', test='', date='', div_name='') {
    getDataLists(dateF)
    getChartByTestandLot(lot, test, date, div_name)
}
function clearLists () {
    dateFromDiv.innerHTML=` <input id="dateFrom" name="date" type="text" list="dateData1" onchange="getChartByDateFrom('getmeasurelistsByDateFrom/'+document.getElementById('dateFrom').value.replace(' ','T')+' '+
                            document.getElementById('dateTo').value.replace(' ','T'), '','','/date/'+$( '#dateFrom' ).val().replace(' ','T'), 'tester')">
                        <input id="dateTo" name="date" type="text" list="dateData1"
                               onchange="getChartByDateFrom('getmeasurelistsByDateFrom/'+document.getElementById('dateFrom').value.replace(' ','T')+' '+
                            document.getElementById('dateTo').value.replace(' ','T'), '','','/date/'+$( '#dateFrom' ).val().replace(' ','T')
                            +' '+$( '#dateTo' ).val().replace(' ','T'), 'tester')">
                        <input id="lotFrom" type="text" list="lotData"
                               onchange="getChartByDateFrom('getmeasurelistsByLot/'+document.getElementById('lotFrom').value,'/'+$( '#lotFrom' ).val(),'','', 'tester')">
                        <input id="testFrom" type="text" list="testData"
                               onchange="getChartByTestandLot('/'+$( '#lotFrom' ).val(),'/'+$( '#testFrom' ).val(),'', 'tester1')">`
    getDataLists('getmeasurelistsByDateFrom/2023-02-04T00:00')
}
function getFirst() {
    setTimeout(
    getChartByDateFrom('getmeasurelistsByDateFrom/2023-02-04T00:00', '/111111', '/444444', 'tester1'), 500
    )
}
getFirst()