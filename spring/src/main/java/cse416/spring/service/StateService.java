package cse416.spring.service;

import com.github.javafaker.Faker;
import org.locationtech.jts.geom.Geometry;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.models.county.County;
import cse416.spring.models.job.Job;
import cse416.spring.models.precinct.Incumbent;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.models.state.State;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StateService {
    State findById(long id);

    State findByStateName(StateName state);

    List<State> findAllStates();

//

//

//    }
//
//

}
