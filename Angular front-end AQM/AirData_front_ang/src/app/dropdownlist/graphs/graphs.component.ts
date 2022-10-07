import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import {
  ChartComponent,
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexTooltip,
  ApexStroke,
  ApexTitleSubtitle,
  ApexAnnotations
} from "ng-apexcharts";
import { AirDataAverageForDay } from 'src/app/objects/airDataAverageForDay';
import { GraphsService } from './graphs.service';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart_airQ: ApexChart;
  xaxis: ApexXAxis;
  stroke: ApexStroke;
  tooltip: ApexTooltip;
  dataLabels: ApexDataLabels;
  title: ApexTitleSubtitle;
  annotations: ApexAnnotations;

};
@Component({
  selector: 'app-graphs',
  templateUrl: './graphs.component.html',
  styleUrls: ['./graphs.component.css']
})
export class GraphsComponent implements OnInit {

  @ViewChild("chart_temp", { static: false })
  chart_airQ!: ChartComponent;

  public chartOptions: ChartOptions;

  private very_low_risk_color = "#00FF00";
  private low_risk_color = "#FFFF2E";
  private medium_risk_color = "#FFBF00";
  private high_risk_color = "#FF5349";
  private very_high_risk_color = "#A8000D";
  private color_opacity = .09;

  public allDataFromDatabase: AirDataAverageForDay[] = [];

  ngOnInit(): void {
    this.service.getAllAirDataAverage().subscribe(
      (response: AirDataAverageForDay[]) => {
        this.allDataFromDatabase = response;
        console.log(this.allDataFromDatabase)
      },
      (err: HttpErrorResponse) => {
        alert('Error on the server has occured');
      }
    );


    const eachLocation = this.allDataFromDatabase.flatMap(airData => airData.location);

    console.log(this.allDataFromDatabase);

  }

  constructor(public service: GraphsService) {

    const seriesNameToData = [{
      name: "PdF",
      data: [12.4, 15, 31.55, 110, 100, 1]
    },
    {
      name: "FF",
      data: [19.4, 10.7, 39.55, 120, 100, 1]
    }];
    // eachLocation = this.uniqByFilter<string>(eachLocation);
    this.chartOptions = {
      series: seriesNameToData,
      chart_airQ: {
        height: 350,
        type: "area"
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: "smooth"
      },
      xaxis: {
        type: "datetime",
        categories: []

      },
      tooltip: {
        x: {
          format: "dd / MM  / yyyy"
        }
      },
      title: {
        text: 'Teplota',
        align: 'center',
        margin: 10,
        offsetX: 0,
        offsetY: 0,
        floating: false,
        style: {
          fontSize: '14px',
          fontWeight: 'bold',
          fontFamily: "Monda",
          color: '#263238'
        }
      },
      annotations: {
        yaxis: [
          {
            y: 0,
            y2: 25,
            borderColor: "#696969",
            fillColor: this.very_low_risk_color,
            opacity: this.color_opacity,
            label: {
              borderColor: "#fff",
              style: {
                fontSize: "10px",
                color: "#333",
                background: this.very_low_risk_color
              },
              text: "Velmi nízká"
            }
          },
          {
            y: 25,
            y2: 50,
            borderColor: "#696969",
            fillColor: this.low_risk_color,
            opacity: this.color_opacity,
            label: {
              borderColor: "#fff",
              style: {
                fontSize: "10px",
                color: "#333",
                background: this.low_risk_color
              },
              text: "Nízká"
            }
          },
          {
            y: 50,
            y2: 75,
            borderColor: "#696969",
            fillColor: this.medium_risk_color,
            opacity: this.color_opacity,
            label: {
              borderColor: "#fff",
              style: {
                fontSize: "10px",
                color: "#333",
                background: this.medium_risk_color
              },
              text: "Střední"
            }
          },
          {
            y: 75,
            y2: 100,
            borderColor: "#696969",
            fillColor: this.high_risk_color,
            opacity: this.color_opacity,
            label: {
              borderColor: "#fff",
              style: {
                fontSize: "10px",
                color: "#333",
                background: this.high_risk_color
              },
              text: "Vysoká"
            }
          },
          {
            y: 100,
            y2: 1000, //TO-DO get highest value and insert it here :: 1000 is staticly typed
            borderColor: "#696969",
            fillColor: this.very_high_risk_color,
            opacity: this.color_opacity,
            label: {
              borderColor: "#fff",
              style: {
                fontSize: "10px",
                color: "#333",
                background: this.very_high_risk_color
              },
              text: "Velmi vysoká"
            }
          },]

      }
    };
  }

  private uniqByFilter<T>(array: T[]) {
    return array.filter((value, index) => array.indexOf(value) === index);
  }
}
