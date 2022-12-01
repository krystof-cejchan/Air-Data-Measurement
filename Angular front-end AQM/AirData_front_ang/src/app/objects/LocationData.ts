/**
 *  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;
    @Column(name = "outofservice", columnDefinition = "boolean default 0", insertable = false, nullable = false)
    private Boolean outOfService;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false, unique = true)
    private String name_short;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = true)
    private BigDecimal latitude;
    @Column(nullable = true)
    private BigDecimal longitude;
    @Column(nullable = true)
    private Double metersAboveTheGroundApprox;
 */
export interface LocationData {
    id: number,
    outofservice: boolean,
    city: string,
    name: string,
    name_short: string,
    latitude: number,
    longitude: number,
    metersAboveTheGroundApprox: number
}   