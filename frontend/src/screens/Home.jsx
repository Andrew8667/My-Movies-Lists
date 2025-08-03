import {
  Container,
  Typography,
  AppBar,
  Toolbar,
  IconButton,
  Avatar,
  Box,
  TextField,
  Button,
  Paper,
  Drawer,
  ListItem,
  ListItemText,
  ListItemButton,
  Menu,
  MenuItem,
  Modal,
  Rating,
  Select,
  InputLabel,
  getScopedCssBaselineUtilityClass,
  Alert,
  FormControl,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useNavigation } from "react-router-dom";
import MenuIcon from "@mui/icons-material/Menu";
import NavigationBar from "../components/NavigationBar";

const Home = function Home() {
  const [search, setSearch] = useState(""); //input inside the enter movie title field
  const [movie, setMovie] = useState(null); //movie that was searched for
  const [errorOpen, setErrorOpen] = useState(false); //controls visibilty status of error message
  const [reviewModalOpen, setReviewModalOpen] = useState(false); //the modal used to add review
  const [stars, setStars] = useState(0); //default number of stars for review
  const [review, setReview] = useState(""); //review assocaited with rating
  const [categoryMovieRating, setCategoryMovieRating] = useState(null); //contains all the user's categories, moves in the categories, and ratings for those movies
  const [selectedCategories, setSelectedCategories] = useState([]); //the categories selected to add movie to
  const [addModal, setAddModal] = useState(false); //used for if the modal is visible or not
  const [messageType,setMessageType] = useState(null); //used to monitor if adding review was successful or not or you updated movie
  const [hasRating, setHasRating] = useState(false); //if true then the user cannot create review since it already exists

  useEffect(() => {
    axios
      .get("http://localhost:8080/category/getUserCategories", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => setCategoryMovieRating(response.data))
      .catch((error) => console.log(error));
  }, []);

  /**
   * Returns the searched for movie and assigns it to movie
   */
  const getMovie = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/getMovie/${search}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      console.log(response.data)
      setMovie(response.data);
      return response.data;
    } catch (error) {
      setErrorOpen(true);
      console.log(error);
      return null;
    }
  };

  const addMovie = async () => {
    try {
      await axios.post(
        "http://localhost:8080/movie/add",
        {
          title: movie[0],
          img: movie[7],
          stars: stars,
          review: review,
          categories: selectedCategories,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      setMessageType('created');
      setReviewModalOpen(false);
      setAddModal(true);
    } catch (error) {
      console.error(error);
      setMessageType('error');
      setAddModal(true);
    }
  };

  /**
   * Gets the stars and description of the rating of the movie if it has already been rated
   */
  const getRating = () => {
    axios
      .get(`http://localhost:8080/rating/get/${movie[0]}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        setStars(response.data.rating);
        setReview(response.data.description);
        setHasRating(true);
      })
      .catch((error) => {
        setHasRating(false);
        console.log(error);
      });
  };

  const getMoviesSelectedCategories = (movie)=>{
    axios.get(`http://localhost:8080/movie/getCategories/${movie}`,{
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
    })
    .then(response=>{
        console.log(response.data)
        setSelectedCategories(response.data)
    })
    .catch(error=>{
        console.log(error)
    })
  }

  const updateMoviesSelectedCategories = (movie)=>{
    console.log(selectedCategories)
    axios.put(`http://localhost:8080/movie/updateCategories/${movie}`,selectedCategories,{
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
    })
    .then(response=>{
        console.log(response.data)
    })
    .catch(error=>{
        console.log(error)
    })
  }

  const updateMovie = (movie) => {
    axios
      .put(
        `http://localhost:8080/rating/update/${movie}`,
        {
          rating: parseInt(stars),
          description: review,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Container
      maxWidth={false}
      sx={{
        backgroundColor: "#000000",
        width: "100vw",
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "flex-start",
        alignItems: "center",
      }}
    >
      <NavigationBar></NavigationBar>
      <Box
        sx={{
          width: "600px",
          display: "flex",
          marginTop: "200px",
          flexDirection: "row",
          justifyContent: "space-evenly",
          alignItems: "center",
        }}
      >
        <TextField
          label="Enter movie title"
          InputProps={{
            style: { color: "#FFFFFF" },
          }}
          InputLabelProps={{
            style: { color: "#FFFFFF" },
          }}
          sx={{
            "& .MuiOutlinedInput-root fieldset": {
              borderColor: "#FFFFFF",
            },
            border: 1,
            borderColor: "#FFFFFF",
            borderRadius: 2,
            width: "400px",
          }}
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          required
        ></TextField>
        <Button
          variant="contained"
          sx={{ backgroundColor: "#db0000" }}
          onClick={() => getMovie()}
        >
          Search
        </Button>
      </Box>
      {movie && (
        <Box
          sx={{
            width: "600px",
            marginTop: "20px",
            display: "flex",
            flexDirection: "row",
            justifyContent: "center",
            alignItems: "center",
            border: 1,
            borderColor: "#FFFFFF",
            borderRadius: 2,
          }}
        >
          <img src={movie[7]} alt={movie[0]}></img>
          <Box
            sx={{
              height: "100%",
              display: "flex",
              flexDirection: "column",
              justifyContent: "space-evenly",
              alignItems: "center",
              overflowY: "auto",
              padding: 1,
            }}
          >
            <Typography
              sx={{ fontFamily: "sans-serif", fontSize: 24, fontWeight: 600 }}
            >
              {movie[0]}
            </Typography>
            <Typography sx={{ fontFamily: "sans-serif", fontSize: 16 }}>
              {movie[1]}
            </Typography>
            <Typography sx={{ fontFamily: "sans-serif", fontSize: 16 }}>
              {movie[2]}
            </Typography>
            <Typography sx={{ fontFamily: "sans-serif", fontSize: 16 }}>
              {movie[3]}
            </Typography>
            <Typography sx={{ fontFamily: "sans-serif", fontSize: 16 }}>
              {movie[4]}
            </Typography>
            <Typography sx={{ fontFamily: "sans-serif", fontSize: 16 }}>
              {movie[5]}
            </Typography>
            <Typography sx={{ fontFamily: "sans-serif", fontSize: 16 }}>
              {movie[6]}
            </Typography>
            <Button
              variant="contained"
              sx={{ backgroundColor: "#db0000" }}
              onClick={() => {
                setReviewModalOpen(true);
                getRating();
                getMoviesSelectedCategories(movie[0])
              }}
            >
              Add
            </Button>
          </Box>
        </Box>
      )}
      {movie && (
        <Modal
          open={reviewModalOpen}
          onClose={() => setReviewModalOpen(false)}
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <Box
            sx={{
              height: "400px",
              width: "350px",
              backgroundColor: "#FFFFFF",
              borderRadius: 3,
              display: "flex",
              flexDirection: "column",
              justifyContent: "space-evenly",
              alignItems: "center",
            }}
          >
            {hasRating && (
              <Alert severity="info">
                You have already rated this movie! 
              </Alert>
            )}
            <Rating
              value={stars}
              onChange={(e) => setStars(e.target.value)}
              required
            ></Rating>
            <TextField
              label="Add review here"
              multiline
              rows={6}
              value={review}
              onChange={(e) => setReview(e.target.value)}
              sx={{ width: "100%" }}
              required
            />
            <FormControl required sx={{ width: "200px" }}>
              <InputLabel id="category-label" sx={{ color: "#000000" }}>
                Choose List(s)
              </InputLabel>
              <Select
                labelId="category-label"
                multiple
                value={selectedCategories}
                onChange={(e) => setSelectedCategories(e.target.value)}
                label="Choose List(s)"
                MenuProps={{
                  PaperProps: {
                    style: {
                      maxHeight: 200,
                      overflowY: "scroll",
                    },
                  },
                }}
                sx={{
                  "& .MuiOutlinedInput-root fieldset": {
                    borderColor: "#000000",
                  },
                  border: 1,
                  borderColor: "#FFFFFF",
                  borderRadius: 2,
                  color: "#000000",
                }}
              >
                {categoryMovieRating?.map((item) => (
                  <MenuItem key={item.id} value={item.id}>
                    {item.title}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <Button
              variant="contained"
              sx={{ backgroundColor: "#db0000" }}
              onClick={() => {
                if(hasRating){
                  updateMoviesSelectedCategories(movie[0])
                  updateMovie(movie[0])
                  setMessageType('updated')
                  setAddModal(true)
                } else {
                  addMovie();
                }
              }}
            >
              {hasRating?"Update":"Create review"}
            </Button>
          </Box>
        </Modal>
      )}
      <Modal
        open={errorOpen}
        onClose={() => {
          setErrorOpen(false)
          setStars(0)
          setReview('')//reset the stars and review so it doesn't show up for next movie
        }}
        sx={{ display: "flex", justifyContent: "center", alignItems: "center" }}
      >
        <Alert severity="error">Error finding movie! Please try again</Alert>
      </Modal>
      <Modal
        open={addModal}
        onClose={() => setAddModal(false)}
        sx={{
          width: "100%",
          height: "100%",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Alert severity={messageType === 'created'  || messageType === 'updated'? "success" : "error"}>
          {messageType === 'created'
            ? "Movie review successfully created!"
            : messageType === 'error'?"Error creating movie review. Please try again!":"Review successfully updated!"}
        </Alert>
      </Modal>
    </Container>
  );
};

export default Home;
