let message=''
function getByDate() {

    let dt =''
    let cdt =''
    let hivAbdt =''
    let hivAgdt =''
    let sdt =''
    let varlist = [dt,cdt,hivAbdt,hivAgdt,sdt]
    let urlist = ['/hbsqc', '/qc', '/hivabqc', '/hivagqc', '/syphqc']
    let namelist = ['HBsAg ', 'anti-HCV ', 'HIV-Ab ', 'HIV-Ag ', 'Syph ']
    let nt=new Date().toLocaleString().split(',')[0]
    for (let i = 0; i < urlist.length; i++) {
        fetch(urlist[i]+'/date')
            .then(res=> res.json()
                .then((data)=>{
                    let dt=new Date(data).toLocaleString()
                    if (dt.split(',')[0]!==nt) {
                        message=message+namelist[i]
                    }
                    // console.log(new Date().toLocaleString())
                    // console.log(new Date(parsed[0], parsed[1], parsed[2]))
                    // console.log(new Date(parsed[0], parsed[1], parsed[2])===new Date().toLocaleString())
                }))
    }
    setTimeout(contrl
        , 500)

}
function contrl () {
    if (message !== '') {
        document.getElementById('but').disabled = true
        document.getElementById('warn').innerHTML=`<h2 style="font-weight: bold; font-size: 20px; color: red">Внесите данные по контролю качества ${message} за текущую дату!</h2>`
        // alert('Внесите данные по контролю качества за текущую дату!')
    } else {
        exp()
        // console.log('ok')
    }
}