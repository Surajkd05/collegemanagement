import React from 'react';
import Highcharts from 'highcharts/highstock';
import HighchartsReact from 'highcharts-react-official';

const options = () => {
return {
    chart: {
        plotShadow: false,
        backgroundColor: null,
        plotBorderWidth: 0,
        height: 250,
        type: 'column'
        },
    title: {
      text: ''
      },
    xAxis: {
      categories: ['ATP', 'UniMoney1', 'YesBank1', 'Mckinsey', 'ATP2', 'Agile', 'Xebia Academy', 'Levis', 'ATP3', 'Big Data', 'XAssessment', 'Finance','Legal Room','Gurgaon Recruitment','IT Room','PMO Room','Marketing','FS Training','Yes Bank2']
      },
    yAxis: {
      visible: false
      },
    tooltip: {
      pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.percentage:.0f}%)<br/>',
      shared: true
      },
    legend: {
        symbolHeight: 12,
        symbolWidth: 12,
        symbolRadius: 0,
        verticalAlign: 'bottom',
        itemStyle: {
        color: '#D0D0D0'
        }
        },
    plotOptions: {
        column: {
        stacking: 'percent',
        borderWidth: 0
        }
        },
    series: [{
      name: 'Available seats',
      data: [13, 17, 24, 17, 12, 14, 16, 13, 11, 14, 15, 16],
      color: "#9D92B2"
    }, {
      name: 'occupied seats',
      data: [2, 7, 4, 7, 2, 4, 6, 3, 1, 4, 5, 6],
      color: "#FF9416"
    }
    
    ],
    credits: {
    enabled: false
    }
    };
    };

export default class BarChart extends React.Component {

render() {
return (
    <div className='commonbox'>
         <div className='commonbox__head'>
             <h2>Utilization Rate</h2>
         </div>
    
         <div className='commonbox__body'>
            <HighchartsReact highcharts={Highcharts} options={options()} />
         </div>
    </div>
    );  
  }
}