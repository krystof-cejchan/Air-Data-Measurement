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
import "../../objects/abs_interfaces/IComponent"
import { generateChartOptions } from "./graphs.generator"
import { openSnackBar } from 'src/app/errors/custom in-page errors/snack-bar/custom-error-snackbar';
import { MatSnackBar } from "@angular/material/snack-bar";

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
  styleUrls: ['./graphs.component.scss']
})

export class GraphsComponent implements OnInit, IComponent {

  @ViewChild("chart_temp", { static: false })
  chart_airQ!: ChartComponent;


  public chartOptionsAirQ: ChartOptions;
  public chartOptionsHum?: ChartOptions = undefined;
  public chartOptionsTemp?: ChartOptions = undefined;

  private very_low_risk_color = "#00FF00";
  private low_risk_color = "#FFFF2E";
  private medium_risk_color = "#FFBF00";
  private high_risk_color = "#FF5349";
  private very_high_risk_color = "#A8000D";
  private color_opacity = .09;

  public allDataFromDatabase: AirDataAverageForDay[] = [];
  private series: ApexAxisChartSeries = [];

  async ngOnInit(): Promise<void> {
    this.service.getAllAirDataAverage().subscribe(
      (response: AirDataAverageForDay[]) => {
        response.forEach(respData => this.allDataFromDatabase.push(respData));
      }, () => {
        openSnackBar(this.snackBar)
      }
    );

    var counter = 0;
    //400ms to wait | max wait for 5s
    const msToWait = 400, msMaxToWait = 5000;
    while (this.allDataFromDatabase.length === 0 && counter < msMaxToWait / msToWait) {
      await new Promise(f => setTimeout(f, msToWait!));
      counter++;
    }
    let dates = [];
    let locations = [];
    locations = this.uniqByFilter<string>(this.allDataFromDatabase.map(it => it.location.name_short));
    dates = this.uniqByFilter(this.allDataFromDatabase.flatMap(it => it.receivedDataDate));
    const seriesAirQToData: ApexAxisChartSeries = [];
    const seriesHumToData: ApexAxisChartSeries = [];
    const seriesTempToData: ApexAxisChartSeries = [];

    locations.forEach(location => {
      seriesAirQToData
        .push({
          name: location,
          data: this.allDataFromDatabase
            .filter(f => f.location.name_short === location)
            .map(filteredData => filteredData.airQualityAvg)
        });

      seriesTempToData
        .push({
          name: location,
          data: this.allDataFromDatabase
            .filter(f => f.location.name_short === location)
            .map(filteredData => filteredData.temperatureAvg)
        });

      seriesHumToData
        .push({
          name: location,
          data: this.allDataFromDatabase
            .filter(f => f.location.name_short === location)
            .map(filteredData => filteredData.humidityAvg)
        });
    });

    this.setNewDataToGraph(seriesAirQToData, dates, 'airQ');
    this.setNewDataToGraph(seriesTempToData, dates, 'temp');
    this.setNewDataToGraph(seriesHumToData, dates, 'hum');

  }

  constructor(public service: GraphsService, private snackBar: MatSnackBar) {
    const seriesNameToData = [{
      name: "Unknown",
      data: [0]
    },
    {
      name: "Server Error",
      data: [500]
    }];
    this.series = seriesNameToData;
    // eachLocation = this.uniqByFilter<string>(eachLocation);
    this.chartOptionsAirQ = {
      series: this.series,
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
        text: 'Kvalita Vzduchu',
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
  getTitle(): string {
    return "Grafy";
  }

  private setNewDataToGraph(series_Data: ApexAxisChartSeries = [], categories_Dates: string[] = [], type: 'airQ' | 'temp' | 'hum') {
    switch (type) {
      case 'airQ':
        this.chartOptionsAirQ = generateChartOptions(series_Data, categories_Dates, type);
        break;
      case 'temp':
        this.chartOptionsTemp = generateChartOptions(series_Data, categories_Dates, type);
        break;
      default:
        this.chartOptionsHum = generateChartOptions(series_Data, categories_Dates, type);
        break;
    }
  }

  private uniqByFilter<T>(array: T[]) {
    return array.filter((value, index) => array.indexOf(value) === index);
  }
}