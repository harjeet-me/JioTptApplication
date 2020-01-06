package com.jio.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jio.tms.web.rest.TestUtil;

public class OwnerOperatorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerOperator.class);
        OwnerOperator ownerOperator1 = new OwnerOperator();
        ownerOperator1.setId(1L);
        OwnerOperator ownerOperator2 = new OwnerOperator();
        ownerOperator2.setId(ownerOperator1.getId());
        assertThat(ownerOperator1).isEqualTo(ownerOperator2);
        ownerOperator2.setId(2L);
        assertThat(ownerOperator1).isNotEqualTo(ownerOperator2);
        ownerOperator1.setId(null);
        assertThat(ownerOperator1).isNotEqualTo(ownerOperator2);
    }
}
