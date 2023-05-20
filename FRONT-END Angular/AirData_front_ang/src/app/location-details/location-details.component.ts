import { Component, OnInit, OnDestroy } from "@angular/core";
import { SubSink } from "subsink";
import { LocationData } from "../objects/LocationData";
import { LocationDetailsService } from "./location-details.service";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['./location-details.component.scss']
})
export class LocationDetailsComponent implements OnInit, OnDestroy {
  private subs = new SubSink()
  locationData!: LocationData;
  constructor(private locationService: LocationDetailsService, private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    const locationId = this.route.snapshot.params['id'];
    this.subs.add(
      this.locationService.getLocationById(locationId).subscribe(
        {
          next: (locationData) => {
            this.locationData = locationData;
          },
          error: () => {
            this.locationData = {
              id: -1, city: "n / a",
              imgUrl: "",
              latitude: -1,
              longitude: 1,
              name: "unknown",
              name_short: "uknown",
              metersAboveTheGroundApprox: -1,
              outofservice: true
            } as LocationData
          }

        }
      )
    )
  }
  ngOnDestroy(): void {
    this.subs.unsubscribe();
  }

}
