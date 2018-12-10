package br.com.amaro.api.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.ReflectionTestUtils.setField;


@RunWith(SpringRunner.class)
public class EsConfigTest {

    @InjectMocks
    private EsConfig esConfig;

    @Before
    public void setup() {
        setField(esConfig, "EsHost", "localhost");
        setField(esConfig, "EsClusterName", "9300");
        setField(esConfig, "EsClusterName", "docker-cluster");
    }

    @Test
    public void shouldReturnEsClient() throws Exception {
        assertNotNull(esConfig.client());
    }

    @Test
    public void shouldReturnEsTemplate() throws Exception {
        assertNotNull(esConfig.elasticsearchTemplate().getClient());
    }

}
