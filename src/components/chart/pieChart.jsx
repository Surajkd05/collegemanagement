import React from 'react';
import Highcharts from 'highcharts/highstock';
import HighchartsReact from 'highcharts-react-official';
import Constants from '../../pages/constants/index';
import axios from 'axios';

const chartOptions = ( payload ) => {

  const { seriesData, occupancyRate } = payload;

  return {
    chart: {
      plotShadow: false,
      backgroundColor: null,
      plotBorderWidth: 0,
      height: 250,
      width:300
    },
    title: {
      text: `<b style="font-size: 22px;color: white">${occupancyRate} %</br><br /><b style="font-size: 16px ;color: #06A99C">Occupancy Rate</b>`,
      align: 'center',
      verticalAlign: 'center',
      y: 90
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
    tooltip: {
        pointFormat: '<b>{point.y}</b>'
    },
    plotOptions: {
      pie: {
        dataLabels: {
          enabled: false
        },
        borderWidth:0,
        center: ['50%', '50%'],
        size: '100%',
        colors: ["#9D92B2", "#FF9416"],
        showInLegend: true
      }
    },
    series: [{
      type: 'pie',
      innerSize: '80%',
      data: seriesData
    }],
    credits: {
      enabled: false
    }
  };
}
export default class PieChart extends React.Component{

  constructor( props ) {
    super( props );
    const { dropdownOptions } = this.props;
    this.state = {
      options: dropdownOptions,
      data:{},
      selected: dropdownOptions[ 0 ].roomId,
      isLoading: true
    };
  }

  componentDidMount() {

    this.fetchData();

  }

  fetchData() {
    axios( Constants.API_URLS.GET_PIECHART_ROOM + this.state.selected, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
    }).then( result => {
        this.setState( { isLoading: false, data: result.data } );
    });
  }

  onSelectChange = ( e ) => {
    this.setState( { isLoading: true, selected: e.currentTarget.value }, this.fetchData );
  }

  getSelectedOptionData() {

    const seriesData = [ 
      ['Available Seats', 0],
      ['Occupied Seats', 0]
    ];
    let occupancyRate = 0;

    const { totalSeats, occupiedSeats, unOccupiedSeats } = this.state.data;
      seriesData[0][1] = unOccupiedSeats;
      seriesData[1][1] = occupiedSeats;

      occupancyRate = ( ( occupiedSeats*100 ) / totalSeats );
    return {
      seriesData,
      occupancyRate: isNaN( occupancyRate ) ? 0 : occupancyRate.toFixed()
    };

  }

  render() {
    
    const { options, selected, isLoading } = this.state;

    return(
      <div className='commonbox'>
          <div className='commonbox__head'>
              <h2>Occupancy Rate</h2>
              <select onChange={ this.onSelectChange }>
                  { 
                    options.map( ( option ) => {
                        return(
                          <option key={ option.roomId } defaultValue={ option.roomId === selected } value={option.roomId}>
                            {  option.roomName }
                          </option>
                        );
                    } ) 
                  }
              </select>
          </div>
          <div className='commonbox__body'>
              {
                isLoading ? 'Loading...' : <HighchartsReact highcharts={Highcharts} options={ chartOptions( this.getSelectedOptionData() )  } />
              }
              
          </div>
      </div>
    );
  }

}
