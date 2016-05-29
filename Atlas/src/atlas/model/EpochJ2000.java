package atlas.model;

import static atlas.model.BodyType.*;
import static atlas.model.Body.Properties.celsiusToKelvin;
import static atlas.model.Body.Properties.KelvinToCelsius;

import java.util.Optional;

public enum EpochJ2000 {

    SUN(new BodyImpl.Builder().name("Sun")
                                .type(STAR)
                                .mass(SOLAR_MASS)
                                .posX(0)
                                .posY(0)
                                .velX((5.374260940168565E-06 * AU) / DAY_SECONDS)
                                .velY((-7.410965396701423E-06 * AU) / DAY_SECONDS)
                                .properties(new Body.Properties(702020*1000, 26 * DAY_SECONDS, 0, null, 5775.00 ))
                                .build()),
    
    EARTH(new BodyImpl.Builder().name("Earth")
                                .type(PLANET)
                                .mass(EARTH_MASS)
                                .posX(-1.756637922977121E-01 * AU)
                                .posY(9.659912850526894E-01 * AU)
                                .velX((-1.722857156974861E-02 * AU) / DAY_SECONDS)
                                .velY((-3.015071224668472E-03 * AU) / DAY_SECONDS)
                                .properties(new Body.Properties(6371*1000, DAY_SECONDS, 0, SUN.getBody(), celsiusToKelvin(14.00) ))
                                .build()),
    ;
    
  //moon
//  px = -1.777871530146587E-01 * AU;
//  py = 9.643743958957228E-01 * AU;
//
//  vx = (-1.690468993933486E-02 * AU) / t;
//  vy = (-3.477070764654480E-03 * AU) / t;
//  this.bodies.add(new BodyImpl("moon", px, py, vx, vy, 734.9e20));
    
    private Body body;
    
    private EpochJ2000(final Body b){
        this.body = b;
    }
    
    public Body getBody(){
        return this.body;
    }
}
