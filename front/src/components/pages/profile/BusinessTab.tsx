import React, { useEffect, useState } from "react";
import { Box, Pagination } from "@mui/material";
import { faker } from "@faker-js/faker";
import BusinessCard, { BusinessData } from "../../parts/BusinessCard";

interface BusinessListData {
  businesses: BusinessData[];
  totalPages: number;
}

function fakeData(page: number): BusinessListData {
  const fakeBusinesses: BusinessData[] = [];
  const itemsPerPage = 10;
  const totalBusinesses = 100;

  for (let i = 0; i < itemsPerPage; i++) {
    const business: BusinessData = {
      id: faker.number.int(),
      name: faker.lorem.word(),
      picture: faker.image.url(),
      description: faker.lorem.paragraph(),
      rating: faker.number.float({ fractionDigits: 2, min: 1, max: 5 }),
    };
    fakeBusinesses.push(business);
  }

  return {
    businesses: fakeBusinesses,
    totalPages: Math.ceil(totalBusinesses / itemsPerPage),
  };
}

export default function BusinessTab() {
  const [data, setData] = useState<BusinessListData | null>(null);
  const [page, setPage] = useState(1);

  function fetchData() {
    const fake = fakeData(page);
    setData(fake);

    // You can replace this with actual API call if needed
    // axios
    //   .get<BusinessListData>(
    //     `${process.env.REACT_APP_BACKEND}/businesses/api-v1/${page}`
    //   )
    //   .then((response) => setData(response.data))
    //   .catch((error) => alert(error.message));
  }

  useEffect(() => {
    fetchData();
  }, [page]);

  const handlePageChange = (
    event: React.ChangeEvent<unknown>,
    value: number
  ) => {
    setPage(value);
  };

  return (
    <Box>
      <Box sx={{ display: "flex", justifyContent: "center" }}>
        <Pagination
          count={data?.totalPages || 1}
          page={page}
          onChange={handlePageChange}
          sx={{ marginTop: 1 }}
          showFirstButton
          showLastButton
        />
      </Box>
      <Box sx={{ overflow: "auto" }}>
        {data?.businesses.map((business) => (
          <BusinessCard key={business.id} business={business} />
        ))}
      </Box>
      {data?.businesses.length !== undefined && data?.businesses.length > 3 && (
        <Box sx={{ display: "flex", justifyContent: "center" }}>
          <Pagination
            count={data?.totalPages || 1}
            page={page}
            onChange={handlePageChange}
            sx={{ marginTop: 1 }}
            showFirstButton
            showLastButton
          />
        </Box>
      )}
    </Box>
  );
}
